package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.models.Seat;
import org.example.book_my_show.models.Show;
import org.example.book_my_show.models.ShowSeat;

import java.util.List;

public interface IShowSeatService {
    List<ShowSeat> getAndLockShowSeats(Show show, List<Seat> seats) throws SeatNotAvailableException;
}
