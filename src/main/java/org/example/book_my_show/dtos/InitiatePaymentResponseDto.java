package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentStatus;
import org.example.book_my_show.models.ResponseStatus;

@Getter
@Setter
public class InitiatePaymentResponseDto {
    private Long paymentId;
    private String referenceNumber;
    private PaymentStatus paymentStatus;
    private ResponseStatus responseStatus;
    private String message;
}

