package org.example.book_my_show.repositories;

import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);

    List<Payment> findAllByTicket(Ticket ticket);
}
