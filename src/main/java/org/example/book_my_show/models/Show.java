package org.example.book_my_show.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Show extends BaseModel {
    private String name;
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Feature> features;

    // Missed:
    private Language language;
}
