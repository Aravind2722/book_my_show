package org.example.book_my_show.repositories;

import org.example.book_my_show.models.SeatType;
import org.example.book_my_show.models.Show;
import org.example.book_my_show.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository <ShowSeatType, Long> {
    List<ShowSeatType> findAllByShowAndSeatTypeIn(Show show, List<SeatType> seatTypes);
}
