package controllers;
import model.Restaurant;
import model.*;
import service.*;

import java.util.List;
import java.util.Scanner;
public class MenuManager {
    private AuthService authService;
    private BookingService bookingService;
    private RestaurantService restaurantService;
    private TableService tableService;
    private UserService userService;
    private Scanner scanner=new Scanner(System.in);
    public MenuManager(AuthService authService, BookingService bookingService, RestaurantService restaurantService, TableService tableService, UserService userService) {
        this.authService=authService;
        this.bookingService=bookingService;
        this.restaurantService=restaurantService;
        this.tableService=tableService;
        this.userService=userService;
    }
    public void start() {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleRegistration();
                    break;
                case 2:
                    handleLogin();
                    break;
                case 3:
                    System.out.println("Thank you for using Sit&Eat. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleRegistration() {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your surname: ");
        String surname = scanner.nextLine();
        System.out.println("Enter your gender(true for male, false for female): ");
        boolean gender =  Boolean.parseBoolean(scanner.nextLine());
        scanner.nextLine();
        boolean registered = userService.registerUser(login, password, name, surname, gender);
        if (registered) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed.");
        }
    }
    private void handleLogin() {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        User user= authService.loginAsUser(login, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName());
            showUserMenu(user);
        }
        else {
            System.out.println("Login failed. Please check your credentials.!");
        }

    }
    private void showUserMenu(User user) {
        boolean loggedIn=true;
        while (loggedIn) {
            System.out.println("Choose an option:");
            System.out.println("1. View Restaurants");
            System.out.println("2. View Available Tables");
            System.out.println("3. Book a Table");
            System.out.println("4. View My Bookings");
            System.out.println("5. Logout");
            System.out.print("Your choice: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer
            switch (userChoice) {
                case 1:
                    restaurantService.getAllRestaurants();
                    break;
                case 2:
                    System.out.print("Enter restaurant ID to view available tables: ");
                    int restaurantId = scanner.nextInt();
                    scanner.nextLine();
                    tableService.getAvailableTables(restaurantId);
                    break;
                case 3:
                    System.out.print("Enter table ID to book: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();
                    bookingService.createBooking(user.getId(), tableId);
                    break;
                case 4:
                    bookingService.getBookingsByUserId(user.getId());
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private void handleViewRestaurants() {
        System.out.println("\nExecuting handleViewRestaurants()..."); // Отладочное сообщение
        try {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            if (restaurants.isEmpty()) {
                System.out.println("No restaurants available.");
            } else {
                for (Restaurant restaurant : restaurants) {
                    System.out.println("ID: " + restaurant.getId() + " | Name: " + restaurant.getName() +
                            " | Location: " + restaurant.getLocation());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching restaurants: " + e.getMessage());
        }
    }

    private void handleViewAvailableTables() {
        System.out.print("\nEnter restaurant ID to view available tables: ");
        int restaurantId = getUserChoice();

        try {
            List<Table> tables = tableService.getAvailableTables(restaurantId);
            if (tables.isEmpty()) {
                System.out.println("No available tables for restaurant ID: " + restaurantId);
            } else {
                for (Table table : tables) {
                    System.out.println("Table ID: " + table.getId());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching tables: " + e.getMessage());
        }
    }

    private void handleBookTable(User user) {
        System.out.print("\nEnter table ID to book: ");
        int tableId = getUserChoice();

        try {
            boolean success = bookingService.createBooking(user.getId(), tableId);
            if (!success) {
                System.out.println("Failed to book table. It may already be reserved.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while booking table: " + e.getMessage());
        }
    }

    private void handleViewMyBookings(User user) {
        System.out.println("\nFetching your bookings...");
        try {
            List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());
            if (bookings.isEmpty()) {
                System.out.println("You have no bookings.");
            } else {
                for (Booking booking : bookings) {
                    System.out.println("Booking ID: " + booking.getId() + " | Table ID: " + booking.getTableId() +
                            " | Time: " + booking.getBookingTime());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching your bookings: " + e.getMessage());
        }
    }

    private int getUserChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}

