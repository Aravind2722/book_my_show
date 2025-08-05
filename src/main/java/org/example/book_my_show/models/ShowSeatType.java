package org.example.book_my_show.models;

public class ShowSeatType {
    // Ideally we could have had the seat type in show seat class itself
    // But, if there are 100 seats in a show, the table will store 100 entries to define the relationship
    // Example:
    //  show 1 - seat 1 - vip
    //  show 1 - seat 2 - vip
    // Here, for the same show, there could be many seats with the same type.
    // Problems:
    // 1. Rows would be more.
    // 2. Only show and seat type would affect the price of a current show, So seat is not needed there
    // Hence, we create a separate mapping class for Show and SeatType

    private Show show;
    private SeatType seatType;
    private int price;
}
