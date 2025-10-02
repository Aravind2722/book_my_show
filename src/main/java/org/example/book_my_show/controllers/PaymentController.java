package org.example.book_my_show.controllers;

import org.example.book_my_show.dtos.*;
import org.example.book_my_show.exceptions.PaymentNotFoundException;
import org.example.book_my_show.exceptions.TicketNotFoundException;
import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.models.Ticket;
import org.example.book_my_show.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public InitiatePaymentResponseDto initiatePayment(InitiatePaymentRequestDto requestDto) {
        InitiatePaymentResponseDto response = new InitiatePaymentResponseDto();
        try {
            Payment payment = paymentService.initiatePayment(
                    requestDto.getTicketId(),
                    requestDto.getAmount(),
                    requestDto.getMode(),
                    requestDto.getProvider()
            );
            response.setPaymentId(payment.getId());
            response.setReferenceNumber(payment.getReferenceNumber());
            response.setPaymentStatus(payment.getPaymentStatus());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Payment initiated");
        } catch (TicketNotFoundException e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage("Something went wrong!");
        }
        return response;
    }

    public ConfirmPaymentResponseDto confirmPayment(ConfirmPaymentRequestDto requestDto) {
        ConfirmPaymentResponseDto response = new ConfirmPaymentResponseDto();
        try {
            Payment payment = paymentService.confirmPayment(requestDto.getPaymentId(), requestDto.getStatus());
            response.setPaymentId(payment.getId());
            response.setPaymentStatus(payment.getPaymentStatus());
            Ticket ticket = payment.getTicket();
            if (ticket != null) {
                response.setTicketId(ticket.getId());
                response.setTicketStatus(ticket.getTicketStatus());
            }
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Payment updated");
        } catch (PaymentNotFoundException | TicketNotFoundException e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage("Something went wrong!");
        }
        return response;
    }
}

