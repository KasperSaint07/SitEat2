package service;

import model.Booking;
import repositories.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean createBooking(int userId, int tableId) {
        LocalDateTime bookingTime = LocalDateTime.now();
        return bookingRepository.addBooking(userId, tableId, bookingTime);
    }

    public boolean cancelBooking(int bookingId) {
        return bookingRepository.deleteBooking(bookingId);
    }

    public List<Booking> getUserBookings(int userId) {
        return bookingRepository.getBookingsByUserId(userId);
    }

    public List<Booking> getBookingsByRestaurant(int restaurantId) {
        return bookingRepository.getBookingsByRestaurantId(restaurantId);
    }
}
