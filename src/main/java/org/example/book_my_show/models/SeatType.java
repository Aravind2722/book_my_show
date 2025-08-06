package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@EqualsAndHashCode(of = "id")
public class SeatType extends BaseModel {
    private String name;
}
