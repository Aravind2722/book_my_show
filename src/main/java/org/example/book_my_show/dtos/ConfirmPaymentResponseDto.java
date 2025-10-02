package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentStatus;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.models.TicketStatus;

@Getter
@Setter
public class ConfirmPaymentResponseDto {
    private Long paymentId;
    private Long ticketId;
    private PaymentStatus paymentStatus;
    private TicketStatus ticketStatus;
    private ResponseStatus responseStatus;
    private String message;
}
