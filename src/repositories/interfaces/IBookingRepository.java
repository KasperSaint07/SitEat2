package repositories.interfaces;

import model.Booking;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookingRepository {
    boolean addBooking(int userId, int tableId, LocalDateTime bookingTime, LocalDateTime bookingEndTime);
    List<Booking> getBookingsByRestaurantId(int restaurantId);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getAllBookings(); // Если вы используете этот метод для освобождения просроченных бронирований
}
