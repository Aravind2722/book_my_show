package org.example.book_my_show.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Screen extends BaseModel {
    private String name;
    private int number;
    @OneToMany
    private List<Seat> seats;
    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Feature> supportedFeatures;
}
