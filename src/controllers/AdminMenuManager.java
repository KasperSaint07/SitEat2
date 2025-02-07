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
    private AdminController adminController;

    public AdminMenuManager(Admin admin, BookingService bookingService, TableService tableService,
                            RestaurantService restaurantService, UserService userService) {
        this.admin = admin;
        this.bookingService = bookingService;
        this.tableService = tableService;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.adminController = new AdminController(bookingService, tableService);
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
                    adminController.viewTables(admin.getRestaurantId());
                    break;
                case 2:
                    adminController.viewBookingsForRestaurant(admin.getRestaurantId());
                    break;
                case 3:
                    adminController.markTableUnavailable(admin.getRestaurantId());
                    break;
                case 4:
                    adminController.releaseTable(admin.getRestaurantId());
                    break;
                case 5:
                    adminController.addTable(admin.getRestaurantId());
                    break;
                case 6:
                    adminController.removeTable(admin.getRestaurantId());
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