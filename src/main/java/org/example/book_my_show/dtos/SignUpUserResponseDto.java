package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.book_my_show.models.ResponseStatus;

@Getter
@Setter
@ToString
public class SignUpUserResponseDto {
//    private String name;
//    private String email;
//    etc
// Only send above details if needed

    private Long userId;
    private ResponseStatus responseStatus;
    private String message;

}
