package org.example.book_my_show.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends BaseModel {
    // 1 ticket will belong to only one user
    // 1 user can take how many ever tickets he wants
    // m : 1
    @ManyToOne
    private User user;

    // 1 ticket can belong to only one show
    // 1 show can have many tickets
    // m : 1
    @ManyToOne
    private Show show;

    // 1 ticket can have multiple seats
    // 1 seat can be belong to multiple tickets (either via a different show, or if the user cancels the ticket, the seats would become available and would be a part of another ticket!)
    // m : m
    @ManyToMany
    private List<Seat> seats;

    // 1 ticket can have multiple partial payments (cashback, promo code, actual money)
    // 1 payment will strictly belong to only one ticket
    // 1 : m
    @OneToMany(mappedBy = "ticket")
    private List<Payment> payments;
    private Date timeOfBooking;

    // Missed:
    private int amount;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

}
