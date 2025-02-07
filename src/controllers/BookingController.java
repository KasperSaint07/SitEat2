package controllers;

import model.Booking;
import model.Restaurant;
import service.BookingService;
import service.TableService;
import model.Table;
import service.RestaurantService;
import java.util.List;
import java.util.Scanner;

public class BookingController {
    private final BookingService bookingService;
    private final TableService tableService;
    private final RestaurantService restaurantService;
    private final Scanner scanner = new Scanner(System.in);

    public BookingController(BookingService bookingService, TableService tableService, RestaurantService restaurantService) {
        this.bookingService = bookingService;
        this.tableService = tableService;
        this.restaurantService = restaurantService;
    }

    public void createBooking(int userId) {
        System.out.print("Enter Restaurant ID to view available tables: ");
        int restaurantId = getUserChoice();

        List<Table> availableTables = tableService.getAvailableTables(restaurantId);
        if (availableTables.isEmpty()) {
            System.out.println("No available tables in this restaurant.");
            return;
        }

        System.out.println("Available Tables:");
        for (int i = 0; i < availableTables.size(); i++) {
            System.out.println("[" + (i + 1) + "] - Table ID: " + availableTables.get(i).getId());
        }

        System.out.print("Enter the number of the table you want to book: ");
        int choice = getUserChoice();
        if (choice < 1 || choice > availableTables.size()) {
            System.out.println("Invalid choice. Booking cancelled.");
            return;
        }

        int tableId = availableTables.get(choice - 1).getId();
        if (bookingService.createBooking(userId, tableId)) {
            System.out.println("Table booked successfully!");
        } else {
            System.out.println("Failed to book table. It might be unavailable.");
        }
    }

    public void viewMyBookings(int userID) {
        System.out.println("\nFetching your bookings...");
        List<Booking> bookings = bookingService.getBookingsByUserId(userID);

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("\nYour Bookings:");
        for (Booking booking : bookings) {
            int restaurantId = tableService.getRestaurantIdByTable(booking.getTableId());
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            String restaurantName = (restaurant != null) ? restaurant.getName() : "Unknown";

            System.out.println("Restaurant: " + restaurantName +
                    " | Table ID: " + booking.getTableId() +
                    " | Booking Time: " + booking.getBookingTime());
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