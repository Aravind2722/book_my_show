package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.PaymentNotFoundException;
import org.example.book_my_show.exceptions.TicketNotFoundException;
import org.example.book_my_show.models.*;
import org.example.book_my_show.repositories.PaymentRepository;
import org.example.book_my_show.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final IShowSeatService showSeatService;

    @Autowired
    public PaymentServiceImpl(TicketRepository ticketRepository,
                              PaymentRepository paymentRepository,
                              IShowSeatService showSeatService) {
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.showSeatService = showSeatService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Payment initiatePayment(Long ticketId, int amount, PaymentMode mode, PaymentProvider provider) throws TicketNotFoundException {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isEmpty()) throw new TicketNotFoundException("Ticket " + ticketId + " not found");
        Ticket ticket = ticketOptional.get();
        if (ticket.getTicketStatus() == TicketStatus.BOOKED || ticket.getTicketStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Ticket is not in a payable state");
        }
        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setAmount(amount);
        payment.setMode(mode);
        payment.setPaymentProvider(provider);
        payment.setTime(new Date());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setReferenceNumber(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Payment confirmPayment(Long paymentId, PaymentStatus status) throws PaymentNotFoundException, TicketNotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) throw new PaymentNotFoundException("Payment " + paymentId + " not found");
        Payment payment = paymentOptional.get();
        payment.setPaymentStatus(status);
        payment.setTime(new Date());
        payment = paymentRepository.save(payment);

        Ticket ticket = payment.getTicket();
        if (ticket == null) throw new TicketNotFoundException("Associated ticket not found");

        // Aggregate current payment state for the ticket
        List<Payment> paymentsForTicket = paymentRepository.findAllByTicket(ticket);
        int paidAmount = paymentsForTicket.stream()
                .filter(p -> p.getPaymentStatus() == PaymentStatus.SUCCESS)
                .mapToInt(Payment::getAmount)
                .sum();
        boolean anyPending = paymentsForTicket.stream().anyMatch(p -> p.getPaymentStatus() == PaymentStatus.PENDING);

        if (paidAmount >= ticket.getAmount()) {
            // mark seats as BOOKED and update ticket status
            showSeatService.markSeatsBooked(ticket.getShow(), ticket.getSeats());
            ticket.setTicketStatus(TicketStatus.BOOKED);
            ticketRepository.save(ticket);
        } else if (!anyPending) {
            // No pending payments and total paid is insufficient => release seats and fail ticket
            showSeatService.releaseSeats(ticket.getShow(), ticket.getSeats());
            ticket.setTicketStatus(TicketStatus.FAILED);
            ticketRepository.save(ticket);
        }
        return payment;
    }
}
