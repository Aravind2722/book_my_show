package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.models.Seat;
import org.example.book_my_show.models.Show;
import org.example.book_my_show.models.ShowSeat;

import java.util.List;

public interface IShowSeatService {
    List<ShowSeat> getAndLockShowSeats(Show show, List<Seat> seats) throws SeatNotAvailableException;

    // After successful payment, mark seats as BOOKED
    void markSeatsBooked(Show show, List<Seat> seats);

    // On payment failure/timeout, release seats back to AVAILABLE
    void releaseSeats(Show show, List<Seat> seats);
}
