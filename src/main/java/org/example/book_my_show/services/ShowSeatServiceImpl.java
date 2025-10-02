package org.example.book_my_show.services;


import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.models.Seat;
import org.example.book_my_show.models.Show;
import org.example.book_my_show.models.ShowSeat;
import org.example.book_my_show.models.ShowSeatStatus;
import org.example.book_my_show.repositories.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShowSeatServiceImpl implements IShowSeatService {
    private final ShowSeatRepository showSeatRepository;

    @Autowired
    public ShowSeatServiceImpl(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }
    // This transaction ensures that no two transactions can access the same show seats at the same time, a row is not explicitly locked until it is accessed...
    // Thus we use explicit locking in the repository using @Lock annotation, so that whenever we access the show seats, they are locked for other transactions
    // If we annotate a method with @Transactional, Spring will create a proxy that wraps the method and manages the transaction for us.
    // Such that even if there are multiple db operations, all of them are executed in a single transaction.
    // So, even if any operation fails, the entire transaction is rolled back, and the database is left in a consistent state.
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10)
    public List<ShowSeat> getAndLockShowSeats(Show show, List<Seat> seats) throws SeatNotAvailableException {
        List<ShowSeat> showSeats = showSeatRepository.findAllByShowAndSeatIn(show, seats);

        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getStatus().equals(ShowSeatStatus.AVAILABLE)) {
                if (
                        (showSeat.getStatus().equals(ShowSeatStatus.LOCKED)) && (TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - showSeat.getLockedAt().getTime()) <= 10)
                ) {
                    throw new SeatNotAvailableException("Something went wrong! Please try again later.");
                }
            }
        }
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        return savedShowSeats;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10)
    public void markSeatsBooked(Show show, List<Seat> seats) {
        List<ShowSeat> showSeats = showSeatRepository.findAllByShowAndSeatIn(show, seats);
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(ShowSeatStatus.BOOKED);
            // keep lockedAt as the time it was booked or set to now
            showSeat.setLockedAt(new Date());
            showSeatRepository.save(showSeat);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10)
    public void releaseSeats(Show show, List<Seat> seats) {
        List<ShowSeat> showSeats = showSeatRepository.findAllByShowAndSeatIn(show, seats);
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(ShowSeatStatus.AVAILABLE);
            showSeat.setLockedAt(null);
            showSeatRepository.save(showSeat);
        }
    }
}
