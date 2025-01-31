package controllers;

import service.BookingService;

import java.util.Scanner;

public class BookingController {
    private final BookingService bookingService;
    private final Scanner scanner = new Scanner(System.in);

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void createBooking(int userId) {
        System.out.print("Enter Table ID to book: ");
        int tableId = scanner.nextInt();
        scanner.nextLine();

        if (bookingService.createBooking(userId, tableId)) {
            System.out.println("Table booked successfully!");
        } else {
            System.out.println("Failed to book table. It might be unavailable.");
        }
    }

    public void viewUserBookings(int userId) {
        System.out.println("Your bookings:");
        bookingService.getUserBookings(userId).forEach(booking ->
                System.out.println("Booking ID: " + booking.getId() + ", Table ID: " + booking.getTableId() + ", Time: " + booking.getBookingTime())
        );
    }

    public void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        if (bookingService.cancelBooking(bookingId)) {
            System.out.println("Booking canceled successfully.");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }
}
