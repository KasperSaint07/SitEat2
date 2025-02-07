package controllers;

import service.BookingService;

import java.util.Scanner;

public class AdminController {
    private final BookingService bookingService;
    private final Scanner scanner = new Scanner(System.in);

    public AdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void viewBookingsForRestaurant(int restaurantId) {
        System.out.println("\nBookings for Restaurant ID: " + restaurantId);
        var bookings = bookingService.getBookingsByRestaurant(restaurantId);

        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this restaurant.");
            return;
        }

        bookings.forEach(booking ->
                System.out.println("Booking ID: " + booking.getId() +
                        " | Table ID: " + booking.getTableId() +
                        " | Time: " + booking.getBookingTime())
        );
    }
}
