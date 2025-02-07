package controllers;

import model.Table;
import service.BookingService;
import service.TableService;

import java.util.List;
import java.util.Scanner;

public class AdminController {
    private final BookingService bookingService;
    private final TableService tableService;
    private final Scanner scanner = new Scanner(System.in);

    public AdminController(BookingService bookingService, TableService tableService) {
        this.bookingService = bookingService;
        this.tableService = tableService;
    }

    public void viewTables(int restaurantId) {
        System.out.println("\nTables for your restaurant:");
        List<Table> tables = tableService.getTablesByRestaurant(restaurantId);
        if (tables.isEmpty()) {
            System.out.println("No tables found.");
        } else {
            for (Table table : tables) {
                String status = table.isAvailable() ? "available" : "unavailable";
                System.out.println("Table ID: " + table.getId() + " - " + status);
            }
        }
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

    public void markTableUnavailable(int restaurantId) {
        System.out.print("\nEnter Table ID to mark as unavailable: ");
        int tableId = getUserChoice();
        int tableRestaurantId = tableService.getRestaurantIdByTable(tableId);

        if (tableRestaurantId != restaurantId) {
            System.out.println("This table does not belong to your restaurant.");
            return;
        }

        boolean success = tableService.reserveTable(tableId);
        if (success) {
            System.out.println("Table " + tableId + " marked as unavailable.");
        } else {
            System.out.println("Failed to update table availability. Please check the ID.");
        }
    }

    private int getUserChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }

    public void releaseTable(int restaurantId) {
        System.out.print("\nEnter Table ID to release (mark as available): ");
        int tableId = getUserChoice();
        int tableRestaurantId = tableService.getRestaurantIdByTable(tableId);

        if (tableRestaurantId != restaurantId) {
            System.out.println("This table does not belong to your restaurant.");
            return;
        }

        boolean success = tableService.releaseTable(tableId);
        if (success) {
            System.out.println("Table " + tableId + " is now available.");
        } else {
            System.out.println("Failed to update table availability. Please check the ID.");
        }
    }

    public void addTable(int restaurantId) {
        boolean success = tableService.addTable(restaurantId);
        if (success) {
            System.out.println("New table added successfully to your restaurant.");
        } else {
            System.out.println("Failed to add new table. Please try again.");
        }
    }

    public void removeTable(int restaurantId) {
        System.out.print("\nEnter Table ID to remove from your restaurant: ");
        int tableId = getUserChoice();
        int tableRestaurantId = tableService.getRestaurantIdByTable(tableId);

        if (tableRestaurantId != restaurantId) {
            System.out.println("This table does not belong to your restaurant.");
            return;
        }

        boolean success = tableService.removeTable(tableId);
        if (success) {
            System.out.println("Table " + tableId + " removed successfully from your restaurant.");
        } else {
            System.out.println("Failed to remove table. Please check the Table ID and try again.");
        }
    }
}
