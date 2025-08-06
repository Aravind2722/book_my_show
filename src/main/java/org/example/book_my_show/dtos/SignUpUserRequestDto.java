package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserRequestDto {
    private String name;
    private int age;
    private String email;
    private String phoneNumber;
    private String password;
}
