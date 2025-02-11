package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Booking {
    private int id;
    private int userId;
    private int tableId;
    private LocalDateTime bookingTime;   // время начала брони
    private LocalDateTime bookingEndTime; // время окончания брони

    // Обновлённый конструктор с временем окончания брони
    public Booking(int id, int userId, int tableId, LocalDateTime bookingTime, LocalDateTime bookingEndTime) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.bookingTime = bookingTime;
        this.bookingEndTime = bookingEndTime;
    }
}
