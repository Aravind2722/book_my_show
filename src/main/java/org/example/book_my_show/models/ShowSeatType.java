package org.example.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShowSeatType extends BaseModel{
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


    // 1 show seat type will belong to 1 particular show (even if there are same show seat types available for other shows, the id those show seat types would be different, as they are separately created for the respective show)
    // 1 show will have many show seat types
    // m : 1
    @ManyToOne
    private Show show;

    // 1 show seat type will be a particular seat type only (1 paticular seat type will not be equal to multiple seat types!)
    // 1 seat type itself can be the same as many show seat types
    // m : 1
    @ManyToOne
    private SeatType seatType;
    private int price;
}
