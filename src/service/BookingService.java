package service;

import model.Booking;
import model.User;
import model.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();

    public void createBooking(User user, Table table, LocalDateTime bookingTime) {
        Booking booking = new Booking(bookings.size() + 1, user.getId(), table.getId(), bookingTime);
        bookings.add(booking);
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUserId() == userId) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}