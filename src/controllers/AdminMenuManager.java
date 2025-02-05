package controllers;

import model.Admin;
import model.Booking;
import model.Table;
import model.User;
import service.BookingService;
import service.RestaurantService;
import service.TableService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminMenuManager {
    private final Admin admin;
    private final BookingService bookingService;
    private final TableService tableService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public AdminMenuManager(Admin admin, BookingService bookingService, TableService tableService,
                            RestaurantService restaurantService, UserService userService) {
        this.admin = admin;
        this.bookingService = bookingService;
        this.tableService = tableService;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public void start() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Tables");
            System.out.println("2. View Bookings");
            System.out.println("3. Mark Table as Unavailable");
            System.out.println("4. Release Table");
            System.out.println("5. Add Table");
            System.out.println("6. Remove Table");
            System.out.println("7. Logout");
            System.out.print("Your choice: ");
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewTables();
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    markTableUnavailable();
                    break;
                case 4:
                    releaseTable();
                    break;
                case 5:
                    addTable();
                    break;
                case 6:
                    removeTable();
                    break;
                case 7:
                    loggedIn = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewTables() {
        System.out.println("\nTables for your restaurant:");
        List<Table> tables = tableService.getTablesByRestaurant(admin.getRestaurantId());
        if (tables.isEmpty()) {
            System.out.println("No tables found.");
        } else {
            for (Table table : tables) {
                String status = table.isAvailable() ? "available" : "unavailable";
                System.out.println("Table ID: " + table.getId() + " - " + status);
            }
        }
    }

    private void viewBookings() {
        System.out.println("\nBookings for your restaurant:");
        List<Booking> bookings = bookingService.getBookingsByRestaurant(admin.getRestaurantId());
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            int count = 1;
            for (Booking booking : bookings) {
                User user = userService.getUserById(booking.getUserId());
                String userInfo = (user != null) ? user.getName() + " " + user.getSurname() : "Unknown";
                System.out.println(count + ". Booking ID: " + booking.getId()
                        + " | Table ID: " + booking.getTableId()
                        + " | Time: " + booking.getBookingTime()
                        + " | Booked by: " + userInfo);
                count++;
            }
        }
    }

    private void markTableUnavailable() {
        System.out.print("\nEnter Table ID to mark as unavailable: ");
        int tableId = getUserChoice();
        int restaurantId = tableService.getRestaurantIdByTable(tableId);
        if (restaurantId != admin.getRestaurantId()) {
            System.out.println("This table does not belong to your restaurant.");
            return;
        }
        boolean success = tableService.reserveTable(tableId, false);
        if (success) {
            System.out.println("Table " + tableId + " marked as unavailable.");
        } else {
            System.out.println("Failed to update table availability. Please check the ID.");
        }
    }

    private void releaseTable() {
        System.out.print("\nEnter Table ID to release (mark as available): ");
        int tableId = getUserChoice();
        int restaurantId = tableService.getRestaurantIdByTable(tableId);
        if (restaurantId != admin.getRestaurantId()) {
            System.out.println("This table does not belong to your restaurant.");
            return;
        }
        boolean success = tableService.releaseTable(tableId, true);
        if (success) {
            System.out.println("Table " + tableId + " is now available.");
        } else {
            System.out.println("Failed to update table availability. Please check the ID.");
        }
    }

    private void addTable() {
        // Добавление столика в ресторан администратора.
        boolean success = tableService.addTable(admin.getRestaurantId());
        if (success) {
            System.out.println("New table added successfully to your restaurant.");
        } else {
            System.out.println("Failed to add new table. Please try again.");
        }
    }

    private void removeTable() {
        System.out.print("\nEnter Table ID to remove from your restaurant: ");
        int tableId = getUserChoice();
        int restaurantId = tableService.getRestaurantIdByTable(tableId);
        if (restaurantId != admin.getRestaurantId()) {
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

    private int getUserChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }
}