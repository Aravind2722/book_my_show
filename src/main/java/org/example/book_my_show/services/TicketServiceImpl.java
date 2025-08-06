package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.SeatNotAvailableException;
import org.example.book_my_show.exceptions.ShowNotAvailableException;
import org.example.book_my_show.exceptions.UserNotFoundException;
import org.example.book_my_show.models.*;
import org.example.book_my_show.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements ITicketService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final TicketRepository ticketRepository;
    private final IShowSeatService showSeatService;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository,
                             ShowRepository showRepository,
                             SeatRepository seatRepository,
                             ShowSeatTypeRepository showSeatTypeRepository,
                             TicketRepository ticketRepository,
                             IShowSeatService showSeatService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.ticketRepository = ticketRepository;
        this.showSeatService = showSeatService;
    }

    @Override
    public Ticket generateTicket(Long userId, Long showId, List<Long> seatIds) throws UserNotFoundException, ShowNotAvailableException, SeatNotAvailableException {
        // Check if user exists, otherwise throw exception
        // Check if show exists, otherwise throw exception
        // Get All the seats, using seat Ids
        // Using show and seats, get the show seats 'for update', in a serializable transaction
        //      - check if all the show seats are available. Or if locked, check if they have been locked more than x mins. If not throw an exception
        //      - If all show seats available, then mark the status of all show seats as LOCKED and update the locked at time, and save them.
        // Commit the transaction
        // create a ticket, and set the attributes
        // Save and return the ticket
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new UserNotFoundException(" User "+userId+" not found!");
        Optional<Show> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()) throw new ShowNotAvailableException(" Show "+showId+" not available!");
        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);

//        List<ShowSeat> savedShowSeats = getAndLockShowSeats(showOptional.get(), seats);
        List<ShowSeat> savedShowSeats = showSeatService.getAndLockShowSeats(showOptional.get(), seats);
//        List<SeatType> seatTypes = new ArrayList<>();
//        for (ShowSeat showSeat : savedShowSeats) seatTypes.add(showSeat.getSeat().getSeatType());
        Map<SeatType, Long> seatTypesCount = savedShowSeats.stream().map(showSeat -> showSeat.getSeat().getSeatType()).collect(Collectors.groupingBy(seatType -> seatType, Collectors.counting()));

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShowAndSeatTypeIn(showOptional.get(), new ArrayList<>(seatTypesCount.keySet()));
        int amount = 0;
        for (ShowSeatType showSeatType : showSeatTypes) amount += (int) (showSeatType.getPrice() * seatTypesCount.get(showSeatType.getSeatType()));
        Ticket ticket = new Ticket();
        ticket.setShow(showOptional.get());
        ticket.setUser(userOptional.get());
        ticket.setSeats(seats);
        ticket.setTimeOfBooking(new Date());
        ticket.setTicketStatus(TicketStatus.PROCESSING);
        ticket.setAmount(amount);
        return ticketRepository.save(ticket);
    }

}
