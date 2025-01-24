package model;

import java.time.LocalDateTime;

//Booking system
public class Booking {
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

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
