package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.PaymentNotFoundException;
import org.example.book_my_show.exceptions.TicketNotFoundException;
import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.PaymentMode;
import org.example.book_my_show.models.PaymentProvider;
import org.example.book_my_show.models.PaymentStatus;

public interface IPaymentService {
    // Create a payment intent against a ticket; keeps ticket in PROCESSING
    Payment initiatePayment(Long ticketId, int amount, PaymentMode mode, PaymentProvider provider) throws TicketNotFoundException;

    // Update payment status; on SUCCESS, may complete ticket and book seats
    Payment confirmPayment(Long paymentId, PaymentStatus status) throws PaymentNotFoundException, TicketNotFoundException;
}
