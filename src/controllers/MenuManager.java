package controllers;
import model.User;
import service.*;

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
        while(true){
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");
            System.out.print("Enter your choice: ");
            int choice=scanner.nextInt();
            scanner.nextLine();
            switch(choice) {
                case 1:
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
        boolean gender = scanner.nextBoolean();
        scanner.nextLine();
        boolean registered = userService.registerUser(login,password);
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
}

