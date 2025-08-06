package org.example.book_my_show.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.models.Seat;
import org.example.book_my_show.models.ShowSeat;

import java.util.List;

@Getter
@Setter
public class GenerateTicketResponseDto {
    private Long ticketId;
//    private List<Seat> seats; // List<String> is enough here, why do we have to send the whole object ?
    private String screenName;
    private List<String> seats;

    // Missed:
    private int amount;
    private ResponseStatus responseStatus;
    private String message;
}
