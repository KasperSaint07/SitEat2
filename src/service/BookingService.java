package service;

import model.Booking;
import repositories.BookingRepository;
import repositories.TableRepository;
import repositories.interfaces.IBookingRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final IBookingRepository bookingRepository;
    private final TableRepository tableRepository;

    public BookingService(BookingRepository bookingRepository, TableRepository tableRepository) {
        this.bookingRepository = bookingRepository;
        this.tableRepository = tableRepository;
    }

    public boolean createBooking(int userId, int tableId) {
        if (!tableRepository.isTableAvailable(tableId)) {
            System.out.println("\n Table " + tableId + " is already reserved! Try another one.");
            return false;
        }

        boolean success = bookingRepository.addBooking(userId, tableId, LocalDateTime.now());
        if (success) {
            tableRepository.updateTableAvailability(tableId, false);
            System.out.println("\n Table " + tableId + " booked successfully!");
        } else {
            System.out.println("\n Booking failed! Try again.");
        }
        return success;
    }

    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepository.getBookingsByUserId(userId);
    }
    public List<Booking> getBookingsByRestaurant(int restaurantId) {
        return bookingRepository.getBookingsByRestaurantId(restaurantId);
    }
}

