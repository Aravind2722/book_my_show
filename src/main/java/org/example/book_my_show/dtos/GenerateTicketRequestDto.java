package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerateTicketRequestDto {
    private Long showId;
    private List<Long> seatIds;
    private Long userId;
}
