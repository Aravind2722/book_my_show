package org.example.book_my_show.services;

import org.example.book_my_show.models.PaymentMode;
import org.example.book_my_show.models.PaymentProvider;
import org.springframework.stereotype.Component;

@Component
public class SelectPaymentProviderStrategy {

    public PaymentProvider select(PaymentMode paymentMode) {
        return switch (paymentMode) {
            case UPI -> PaymentProvider.RAZOR_PAY;
            case CASHBACK -> PaymentProvider.PAYU_MONEY;
            case WALLET -> PaymentProvider.CCAVENUE;
            default -> PaymentProvider.SELF;
        };
    }
}
