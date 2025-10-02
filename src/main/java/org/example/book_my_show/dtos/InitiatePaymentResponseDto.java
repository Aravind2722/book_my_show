package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentMode;
import org.example.book_my_show.models.PaymentProvider;
import org.example.book_my_show.models.ResponseStatus;

@Getter
@Setter
public class InitiatePaymentResponseDto {
    private Double amount;
    private PaymentMode paymentMode;
    private PaymentProvider paymentProvider;
    private String referenceNumber;
    private ResponseStatus responseStatus;
    private String message;

}
