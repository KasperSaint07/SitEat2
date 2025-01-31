package controllers;

import model.Admin;
import service.BookingService;
import service.TableService;

import java.util.Scanner;

public class AdminController {
    private final BookingService bookingService;
    private final TableService tableService;
    private final Scanner scanner = new Scanner(System.in);

    public AdminController(BookingService bookingService, TableService tableService) {
        this.bookingService = bookingService;
        this.tableService = tableService;
    }

    public void viewBookingsForRestaurant(int restaurantId) {
        System.out.println("Bookings for Restaurant ID: " + restaurantId);
        bookingService.getBookingsByRestaurant(restaurantId).forEach(booking ->
                System.out.println("Booking ID: " + booking.getId() + ", Table ID: " + booking.getTableId() + ", Time: " + booking.getBookingTime())
        );
    }

    public void manageTables(int restaurantId) {
        System.out.println("1. Add Table");
        System.out.println("2. Remove Table");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                if (tableService.addTable(restaurantId)) {
                    System.out.println("Table added successfully.");
                } else {
                    System.out.println("Failed to add table.");
                }
            }
            case 2 -> {
                System.out.print("Enter table ID to remove: ");
                int tableId = scanner.nextInt();
                scanner.nextLine();
                if (tableService.removeTable(tableId)) {
                    System.out.println("Table removed successfully.");
                } else {
                    System.out.println("Failed to remove table.");
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }
}
