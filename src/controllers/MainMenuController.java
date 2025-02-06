package controllers;

import model.Admin;
import model.User;
import service.AuthService;
import service.BookingService;
import service.RestaurantService;
import service.TableService;
import service.UserService;

import java.util.Scanner;

public class MainMenuController {
    private final AuthService authService;
    private final BookingService bookingService;
    private final RestaurantService restaurantService;
    private final TableService tableService;
    private final UserService userService;
    private final MenuManager menuManager;  // для регистрации и пользовательского входа
    private final Scanner scanner = new Scanner(System.in);

    public MainMenuController(AuthService authService, BookingService bookingService,
                              RestaurantService restaurantService, TableService tableService,
                              UserService userService, MenuManager menuManager) {
        this.authService = authService;
        this.bookingService = bookingService;
        this.restaurantService = restaurantService;
        this.tableService = tableService;
        this.userService = userService;
        this.menuManager = menuManager;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Register User");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    menuManager.registerUser();
                    break;
                case 2:
                    User user = menuManager.loginUser();
                    if (user != null) {
                        menuManager.showUserMenu(user);
                    }
                    break;
                case 3:
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    Admin admin = authService.loginAsAdmin(adminUsername, adminPassword);
                    if (admin != null) {
                        System.out.println("Login successful! Welcome, admin " + admin.getUsername());
                        new AdminMenuManager(admin, bookingService, tableService, restaurantService, userService).start();
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
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
