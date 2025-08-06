package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;

    // Missed:
    private int age; // may be to check eligibility against movie certificates
    private String phoneNumber;
}
