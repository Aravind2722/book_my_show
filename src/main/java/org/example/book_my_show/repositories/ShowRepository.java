package org.example.book_my_show.repositories;

import org.example.book_my_show.models.Show;
import org.hibernate.query.spi.QueryOptionsAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository <Show, Long> {
    Optional<Show> findById(Long showId);
}
