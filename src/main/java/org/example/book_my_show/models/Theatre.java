package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Theatre extends BaseModel {
    private String name;
    private String address;
    @OneToMany
    private List<Screen> screens;

    // Missed:
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
