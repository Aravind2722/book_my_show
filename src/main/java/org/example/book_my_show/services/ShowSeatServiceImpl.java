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
}
