package org.example.book_my_show.repositories;

import org.example.book_my_show.models.Ticket;
import org.example.book_my_show.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository <Ticket, Long> {
    Ticket save(Ticket ticket);
}
