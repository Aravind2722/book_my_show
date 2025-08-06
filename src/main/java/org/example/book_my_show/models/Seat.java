package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat extends BaseModel {
    private String seatNumber;
    private int rowNumber;
    private int columnNumber;

    @ManyToOne
    private SeatType seatType;

}
