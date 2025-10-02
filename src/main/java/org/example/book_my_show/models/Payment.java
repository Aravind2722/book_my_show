package org.example.book_my_show.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Payment extends BaseModel {
    private String referenceNumber;
    @Enumerated(value = EnumType.STRING)
    private PaymentMode mode;
    private Date time;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(value = EnumType.STRING)
    private PaymentProvider paymentProvider;

    // Missed:
    private Double amount;

    // 1 payment would belong to only 1 ticket strictly
    // 1 ticket can have many partial payments
    // m : 1
    @ManyToOne
//    @JoinColumn(name = "ticket_id")
    private Ticket ticket; // check if needed

}
