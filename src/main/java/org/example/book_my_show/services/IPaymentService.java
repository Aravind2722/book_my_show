package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.InvalidPaymentException;
import org.example.book_my_show.exceptions.InvalidTicketException;
import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.PaymentMode;
import org.example.book_my_show.models.PaymentStatus;

public interface IPaymentService {
    Payment initiatePayment(Long ticketId, Double amount, PaymentMode paymentMode) throws InvalidTicketException;
    Payment confirmPayment(Long paymentId, PaymentStatus status) throws InvalidPaymentException;
}
