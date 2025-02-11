package service;

import model.Booking;
import repositories.BookingRepository;
import repositories.TableRepository;
import repositories.interfaces.IBookingRepository;
import service.interfaces.IBookingService;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService implements IBookingService {
    private final IBookingRepository bookingRepository;
    private final TableRepository tableRepository;
    public BookingService(BookingRepository bookingRepository, TableRepository tableRepository) {
        this.bookingRepository = bookingRepository;
        this.tableRepository = tableRepository;
    }
    @Override
    public boolean createBooking(int userId, int tableId, int durationInMinutes) {
        // Сначала освобождаем просроченные бронирования
        releaseExpiredBookings();

        if (!tableRepository.isTableAvailable(tableId)) {
            System.out.println("\nTable " + tableId + " is already reserved! Try another one.");
            return false;
        }

        LocalDateTime bookingStart = LocalDateTime.now();
        LocalDateTime bookingEnd = bookingStart.plusMinutes(durationInMinutes);

        boolean success = bookingRepository.addBooking(userId, tableId, bookingStart, bookingEnd);
        if (success) {
            tableRepository.updateTableAvailability(tableId, false);
            System.out.println("\nTable " + tableId + " booked successfully from " + bookingStart + " until " + bookingEnd);
        } else {
            System.out.println("\nBooking failed! Try again.");
        }
        return success;
    }
    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepository.getBookingsByUserId(userId);
    }
    @Override
    public List<Booking> getBookingsByRestaurant(int restaurantId) {
        return bookingRepository.getBookingsByRestaurantId(restaurantId);
    }

    @Override
    public void releaseExpiredBookings() {
        List<Booking> allBookings = bookingRepository.getAllBookings();
        LocalDateTime now = LocalDateTime.now();
        for (Booking booking : allBookings) {
            // Если время окончания брони отсутствует, пропускаем эту запись
            if (booking.getBookingEndTime() == null) {
                continue;
            }
            if (booking.getBookingEndTime().isBefore(now)) {
                if (!tableRepository.isTableAvailable(booking.getTableId())) {
                    tableRepository.updateTableAvailability(booking.getTableId(), true);
                    System.out.println("Table " + booking.getTableId() + " has been released (booking expired).");
                }
            }
        }
    }

}

