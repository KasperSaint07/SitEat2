package service;

import model.Booking;
import repositories.BookingRepository;
import repositories.TableRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final TableRepository tableRepository;

    public BookingService(BookingRepository bookingRepository, TableRepository tableRepository) {
        this.bookingRepository = bookingRepository;
        this.tableRepository = tableRepository;
    }
    public boolean createBooking(int userId, int tableId) {
        if (!tableRepository.isTableAvailable(tableId)) { // Проверка занятости
            System.out.println("Table is already reserved!");
            return false;
        }

        boolean success = bookingRepository.addBooking(userId, tableId, LocalDateTime.now());
        if (success) {
            tableRepository.updateTableAvailability(tableId, false);
            System.out.println("Table booked successfully!");
        }
        return success;
    }

    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepository.getBookingsByUserId(userId);
    }
}
