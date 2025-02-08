package repositories.interfaces;
import model.Booking;
import java.util.List;
import java.time.LocalDateTime;

public interface IBookingRepository {
    boolean addBooking(int userId,int tableId, LocalDateTime bookingtime);
    List<Booking> getBookingsByRestaurantId(int restaurantId);
    List<Booking> getBookingsByUserId(int userId);
}

