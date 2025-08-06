package org.example.book_my_show.controllers;


import org.example.book_my_show.dtos.GenerateTicketRequestDto;
import org.example.book_my_show.dtos.GenerateTicketResponseDto;
import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.exceptions.ShowNotAvailableException;
import org.example.book_my_show.exceptions.UserNotFoundException;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.models.Seat;
import org.example.book_my_show.models.Ticket;
import org.example.book_my_show.services.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.stream.Collectors;

@Controller
public class TicketController {
    private ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }
    public GenerateTicketResponseDto generateTicket(GenerateTicketRequestDto requestDto) {
        GenerateTicketResponseDto responseDto = new GenerateTicketResponseDto();
        try {
            Ticket ticket = ticketService.generateTicket(requestDto.getUserId(), requestDto.getShowId(), requestDto.getSeatIds());
            responseDto.setTicketId(ticket.getId());
            responseDto.setAmount(ticket.getAmount());
            responseDto.setSeats(ticket.getSeats().stream().map(Seat::getSeatNumber).collect(Collectors.toList()));
            responseDto.setScreenName(ticket.getShow().getScreen().getName());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("Ticket generated successfully!");
        } catch (UserNotFoundException | ShowNotAvailableException | SeatNotAvailableException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage("Something went wrong!");
        }
        return responseDto;
    }
}
