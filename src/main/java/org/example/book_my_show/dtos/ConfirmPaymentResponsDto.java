package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentStatus;
import org.example.book_my_show.models.ResponseStatus;

@Getter
@Setter
public class ConfirmPaymentResponsDto {
    private Long paymentId;
    private String referenceNumber;
    private String message;
    private Double amount;
    private PaymentStatus paymentStatus;
    private Long ticketId;
    private ResponseStatus responseStatus;
}
