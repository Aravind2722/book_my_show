package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.PaymentMode;
import org.example.book_my_show.models.PaymentProvider;

@Getter
@Setter
public class InitiatePaymentRequestDto {
    private Long ticketId;
    private int amount;
    private PaymentMode mode;
    private PaymentProvider provider;
}

