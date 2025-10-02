package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentMode;

@Getter
@Setter
public class InitiatePaymentRequestDto {
    private Long ticketId;
    private Double amountl;
    private PaymentMode paymentMode;
}
