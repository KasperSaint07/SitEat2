package model;

import java.time.LocalDateTime;

public class Booking {
    private int id; // ID брони
    private int userId; // ID пользователя, который сделал бронь
    private int tableId; // ID столика
    private LocalDateTime bookingTime; // Время бронирования

    public Booking(int id, int userId, int tableId, LocalDateTime bookingTime) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.bookingTime = bookingTime;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getTableId() {
        return tableId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
}
