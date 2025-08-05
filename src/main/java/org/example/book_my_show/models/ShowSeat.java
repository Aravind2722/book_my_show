package org.example.book_my_show.models;

import java.util.Date;

public class ShowSeat {
    private Show show;
    private Seat seat;
    private ShowSeatStatus status;

    // Missed:
    private Date lockedAt; // To check if show seat locked status for more than x mins
}
