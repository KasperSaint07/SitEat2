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
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Restaurants");
            System.out.println("2. View Available Tables");
            System.out.println("3. Book a Table");
            System.out.println("4. View My Bookings");
            System.out.println("5. Logout");
            System.out.print("Your choice: ");
            int userChoice = getUserChoice(); // Используем уже существующий метод для ввода числа

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
                // Выводим порядковый номер, а не table.getId()
                System.out.println("[" + index + "] - is available");
                index++;
            }
        }
    }





    private void handleBookTable(User user) {
        System.out.print("\nEnter restaurant ID for booking: ");
        int restaurantId = getUserChoice();

        // Получаем список доступных столиков для выбранного ресторана
        List<Table> tables = tableService.getAvailableTables(restaurantId);
        if (tables.isEmpty()) {
            System.out.println("No available tables found for restaurant ID: " + restaurantId);
            return;
        }

        // Отображаем столики с порядковой нумерацией
        System.out.println("Available Tables:");
        int index = 1;
        for (Table table : tables) {
            System.out.println("[" + index + "] - is available");
            index++;
        }

        // Запрашиваем выбор у пользователя (порядковый номер)
        System.out.print("Enter the number corresponding to the table you want to book: ");
        int choice = getUserChoice();

        // Проверяем корректность выбора
        if (choice < 1 || choice > tables.size()) {
            System.out.println("Invalid table selection.");
            return;
        }

        // Получаем реальный ID выбранного столика
        int actualTableId = tables.get(choice - 1).getId();

        // Пытаемся создать бронирование с использованием реального ID столика
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
                // Получаем id ресторана по tableId
                int restaurantId = tableService.getRestaurantIdByTable(booking.getTableId());
                // Получаем объект ресторана, чтобы вывести его название
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
                int choice = Integer.parseInt(scanner.nextLine().trim());
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}

