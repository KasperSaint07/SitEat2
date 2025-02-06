package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//Booking system
@Setter
@Getter
public class Booking {
    // Getters and Setters
    private int id;                 // ID брони
    private int userId;             // ID пользователя
    private int tableId;            // ID столика
    private LocalDateTime bookingTime; // Время бронирования

    public Booking(int id, int userId, int tableId, LocalDateTime bookingTime) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.bookingTime = bookingTime;
    }

}
