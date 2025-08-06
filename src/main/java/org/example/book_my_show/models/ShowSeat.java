package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class ShowSeat extends BaseModel {
    @ManyToOne
    // 1 show seat will belong to 1 show
    // 1 show will have many show seats
    // m : 1
    private Show show;

    // 1 show seat will point to one exact seat at the show
    // 1 seat can be many show seats. (other shows can happen on the same seat)
    @ManyToOne
    private Seat seat;

    @Enumerated(value = EnumType.STRING)
    private ShowSeatStatus status;

    // Missed:
    private Date lockedAt; // To check if show seat locked status for more than x mins
}
