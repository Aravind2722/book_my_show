package org.example.book_my_show.models;

import java.util.Date;

public class Payment {
    private String referenceNumber;
    private PaymentMode mode;
    private Date time;
    private PaymentStatus paymentStatus;
    private PaymentProvider paymentProvider;

    // Missed:
    private int amount;
    private Ticket ticket; // check if needed

}
