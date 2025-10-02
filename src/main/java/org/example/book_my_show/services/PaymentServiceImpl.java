package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.InvalidPaymentException;
import org.example.book_my_show.exceptions.InvalidTicketException;
import org.example.book_my_show.models.*;
import org.example.book_my_show.repositories.PaymentRepository;
import org.example.book_my_show.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final TicketRepository ticketRepository;
    private final SelectPaymentProviderStrategy selectPaymentProviderStrategy;
    private final PaymentRepository paymentRepository;
    private final ShowSeatServiceImpl showSeatServiceImpl;

    @Autowired
    public PaymentServiceImpl(TicketRepository ticketRepository, SelectPaymentProviderStrategy selectPaymentProviderStrategy, PaymentRepository paymentRepository, ShowSeatServiceImpl showSeatServiceImpl) {
        this.ticketRepository = ticketRepository;
        this.selectPaymentProviderStrategy = selectPaymentProviderStrategy;
        this.paymentRepository = paymentRepository;
        this.showSeatServiceImpl = showSeatServiceImpl;
    }

    @Override
    public Payment initiatePayment(Long ticketId, Double amount, PaymentMode paymentMode) throws InvalidTicketException {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (
                ticketOptional.isEmpty() ||
                ticketOptional.get().getTicketStatus().equals(TicketStatus.BOOKED) ||
                ticketOptional.get().getTicketStatus().equals(TicketStatus.CANCELLED) ||
                ticketOptional.get().getTicketStatus().equals(TicketStatus.FAILED)
        ) {
            throw new InvalidTicketException("Ticket with ID " + ticketId + " is not found (or) valid.");
        }

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setTicket(ticketOptional.get());
        payment.setMode(paymentMode);
        payment.setPaymentProvider(selectPaymentProviderStrategy.select(payment.getMode()));
        payment.setReferenceNumber(UUID.randomUUID().toString());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10)
    public Payment confirmPayment(Long paymentId, PaymentStatus status) throws InvalidPaymentException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) throw new InvalidPaymentException("Payment with ID " + paymentId + " is not found.");
        Payment payment = paymentOptional.get();
        payment.setPaymentStatus(status);
        payment.setTime(new Date());
        paymentRepository.save(payment);

        if (payment.getTicket() == null) throw new InvalidPaymentException("Payment with ID " + paymentId + " is not associated with any ticket.");
        Ticket ticket = payment.getTicket();

        List<Payment> paymentsToTicket = paymentRepository.findAllByTicket(ticket);
        Double totalAmount = paymentsToTicket.stream().mapToDouble(Payment::getAmount).sum();
        boolean anyPending = paymentsToTicket.stream().anyMatch(p -> p.getPaymentStatus().equals(PaymentStatus.PENDING));
        if (totalAmount >= ticket.getAmount()) {
            showSeatServiceImpl.markShowSeatsBooked(ticket.getShow(), ticket.getSeats());
            ticket.setTicketStatus(TicketStatus.BOOKED);
            ticketRepository.save(ticket);
        }else if (!anyPending) {
            // We have to unblock all the seats that are associated with this ticket
            ticket.setTicketStatus(TicketStatus.FAILED);
            ticketRepository.save(ticket);
        }

        return payment;
    }
}
