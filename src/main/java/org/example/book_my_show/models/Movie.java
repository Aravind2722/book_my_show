package org.example.book_my_show.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel {
    private String name;
    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Language> languages;
    private String duration;
}
