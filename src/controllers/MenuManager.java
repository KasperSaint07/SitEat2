package controllers;

import model.Restaurant;
import model.*;
import service.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private AuthService authService;
    private BookingService bookingService;
    private RestaurantService restaurantService;
    private TableService tableService;
    private UserService userService;
    private Scanner scanner = new Scanner(System.in);

    public MenuManager(AuthService authService, BookingService bookingService, RestaurantService restaurantService, TableService tableService, UserService userService) {
        this.authService = authService;
        this.bookingService = bookingService;
        this.restaurantService = restaurantService;
        this.tableService = tableService;
        this.userService = userService;
    }

    // Новый публичный метод для регистрации пользователя
    public void registerUser() {
        handleRegistration();
    }

    // Скрытые (private) методы, которые уже реализованы:
    private void handleRegistration() {
        String login, password, name, surname;
        boolean gender;
        while (true) {
            System.out.println("===Registration Menu===");
            System.out.print("Enter login: ");
            login = scanner.nextLine().trim();
            if (login.isEmpty()) {
                System.out.println("Login cannot be empty. Please try again.");
                continue;
            }
            if (userService.isLoginTaken(login)) {  // Проверяем, существует ли логин
                System.out.println("This login is already taken. Please choose another.");
                continue;
            }
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
                continue;
            }
            System.out.print("Enter your name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
                continue;
            }
            System.out.print("Enter your surname: ");
            surname = scanner.nextLine().trim();
            if (surname.isEmpty()) {
                System.out.println("Surname cannot be empty. Please try again.");
                continue;
            }
            System.out.print("Enter your gender (true for male, false for female): ");
            String genderInput = scanner.nextLine().trim();
            if (!genderInput.equalsIgnoreCase("true") && !genderInput.equalsIgnoreCase("false")) {
                System.out.println("Invalid gender input. Please enter 'true' or 'false'.");
                continue;
            }
            gender = Boolean.parseBoolean(genderInput);

            boolean registered = userService.registerUser(login, password, name, surname, gender);
            if (registered) {
                System.out.println("Registration successful!");
                break;
            } else {
                System.out.println("Registration failed. Try again.");
            }
        }
    }

    // Новый публичный метод для входа пользователя (возвращает объект User)
    public User loginUser() {
        while (true) {
            System.out.println("\n===Login Menu===");
            System.out.print("Enter login: ");
            String login = scanner.nextLine().trim();
            if (login.isEmpty()) {
                System.out.println("Login cannot be empty. Please try again.");
                continue;
            }
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
                continue;
            }
            User user = authService.loginAsUser(login, password);
            if (user != null) {
                System.out.println("Login successful! Welcome, " + user.getName());
            } else {
                System.out.println("Login failed. Please check your credentials.");
            }
            return user;
        }
    }

    public Admin loginAdmin() {
        while (true) {
            System.out.println("\n===Login Admin Menu===");
            System.out.print("Enter admin username: ");
            String adminUsername = scanner.nextLine().trim();
            if (adminUsername.isEmpty()) {
                System.out.println("Login cannot be empty. Please try again.");
                continue;
            }
            System.out.print("Enter admin password: ");
            String adminPassword = scanner.nextLine().trim();
            if (adminPassword.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
                continue;
            }
            Admin admin = authService.loginAsAdmin(adminUsername, adminPassword);
            if (admin != null) {
                System.out.println("Login successful! Welcome, admin " + admin.getUsername());
            } else {
                System.out.println("Invalid admin credentials.");
            }
            return admin;
        }
    }

    public void showUserMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n---User Menu---");
            System.out.println("Choose an option:");
            System.out.println("1. View Restaurants");
            System.out.println("2. View Available Tables");
            System.out.println("3. Book a Table");
            System.out.println("4. View My Bookings");
            System.out.println("5. Logout");
            System.out.print("Your choice: ");
            int userChoice = getUserChoice();
            switch (userChoice) {
                case 1:
                    handleViewRestaurants();
                    break;
                case 2:
                    handleViewAvailableTables();
                    break;
                case 3:
                    handleBookTable(user);
                    break;
                case 4:
                    handleViewMyBookings(user);
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
        System.out.println("\nAvailable Restaurants:");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants found.");
        } else {
            for (Restaurant restaurant : restaurants) {
                System.out.println("[" + restaurant.getId() + "] - " + restaurant.getName());
            }
        }
    }

    private void handleViewAvailableTables() {
        System.out.print("\nEnter restaurant ID to view available tables: ");
        int restaurantId = getUserChoice();
        List<Table> tables = tableService.getAvailableTables(restaurantId);
        if (tables.isEmpty()) {
            System.out.println("No available tables found for restaurant ID: " + restaurantId);
        } else {
            System.out.println("Available Tables:");
            int index = 1;
            for (Table table : tables) {
                System.out.println("[" + index + "] - is available");
                index++;
            }
        }
    }

    private void handleBookTable(User user) {
        System.out.print("\nEnter restaurant ID for booking: ");
        int restaurantId = getUserChoice();
        List<Table> tables = tableService.getAvailableTables(restaurantId);
        if (tables.isEmpty()) {
            System.out.println("No available tables found for restaurant ID: " + restaurantId);
            return;
        }
        System.out.println("Available Tables:");
        int index = 1;
        for (Table table : tables) {
            System.out.println("[" + index + "] - is available");
            index++;
        }
        System.out.print("Enter the number corresponding to the table you want to book: ");
        int choice = getUserChoice();
        if (choice < 1 || choice > tables.size()) {
            System.out.println("Invalid table selection.");
            return;
        }
        int actualTableId = tables.get(choice - 1).getId();
        boolean success = bookingService.createBooking(user.getId(), actualTableId);
        if (success) {
            System.out.println("Table booked successfully!");
        } else {
            System.out.println("Booking failed. Please try again.");
        }
    }

    private void handleViewMyBookings(User user) {
        System.out.println("\nFetching your bookings...");
        List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\nYour Bookings:");
            for (Booking booking : bookings) {
                int restaurantId = tableService.getRestaurantIdByTable(booking.getTableId());
                Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
                String restaurantName = (restaurant != null) ? restaurant.getName() : "Unknown";
                System.out.println("User: " + user.getName() +
                        " | Restaurant: " + restaurantName +
                        " | Table ID: " + booking.getTableId() +
                        " | Booking Time: " + booking.getBookingTime());
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
