package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.exceptions.ShowNotAvailableException;
import org.example.book_my_show.exceptions.UserNotFoundException;
import org.example.book_my_show.models.Ticket;

import java.util.List;

public interface ITicketService {
    Ticket generateTicket(Long userId, Long showId, List<Long> seatIds) throws UserNotFoundException, ShowNotAvailableException, SeatNotAvailableException;
}
