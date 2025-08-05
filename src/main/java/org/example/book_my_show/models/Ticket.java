package org.example.book_my_show.models;

import java.util.Date;
import java.util.List;

public class Ticket {
    private User user;
    private Show show;
    private List<Seat> seats;
    private List<Payment> payments;
    private Date timeOfBooking;

    // Missed:
    private int amount;

}
