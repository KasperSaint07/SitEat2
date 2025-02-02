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
}
