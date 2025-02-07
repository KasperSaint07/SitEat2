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
    private TableController tableController;
    private BookingController bookingController;
    private RestaurantController restaurantController;

    public MenuManager(AuthService authService, BookingService bookingService, RestaurantService restaurantService, TableService tableService, UserService userService) {
        this.authService = authService;
        this.bookingService = bookingService;
        this.restaurantService = restaurantService;
        this.tableService = tableService;
        this.userService = userService;
        this.tableController = new TableController(tableService);
        this.bookingController = new BookingController(bookingService, tableService, restaurantService);
        this.restaurantController = new RestaurantController(restaurantService);
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
            System.out.println("\n===Registration Menu===");
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
                    restaurantController.viewRestaurants();
                    break;
                case 2:
                    tableController.viewAvailableTables();
                    break;
                case 3:
                    bookingController.createBooking(user.getId());
                    break;
                case 4:
                    bookingController.viewMyBookings(user.getId());
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
