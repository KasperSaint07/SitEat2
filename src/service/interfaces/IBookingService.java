package service.interfaces;
import java.util.List;

import model.Booking;
import service.BookingService;

public interface IBookingService {
    boolean createBooking(int userId, int tableId, int durationInMinutes);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getBookingsByRestaurant(int restaurantId);

    void releaseExpiredBookings(); // Новый метод для освобождения столов по истечении срока брони
}
