package org.example.book_my_show.repositories;

import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);
    List<Payment> findAllByTicket(Ticket ticket);
}
