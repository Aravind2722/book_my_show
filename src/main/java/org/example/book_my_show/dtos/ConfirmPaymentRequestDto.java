package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentStatus;

@Getter
@Setter
public class ConfirmPaymentRequestDto {
    private Long paymentId;
    private PaymentStatus paymentStatus;
}
