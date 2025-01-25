import Interfaces.IDB;
import model.Booking;
import model.Restaurant;
import model.Table;
import model.User;
import service.BookingService;
import service.RestaurantService;
import service.TableService;
import service.UserService;
import service.PostgresDB;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            //connect
            IDB db = new PostgresDB();


            UserService userService = new UserService(db.getConnection());
            RestaurantService restaurantService = new RestaurantService(db.getConnection());
            TableService tableService = new TableService(db.getConnection());
            BookingService bookingService = new BookingService(db.getConnection());

            //Menu
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome to Sit&Eat! Choose an option:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                switch (choice) {
                    case 1:
                        // Regist
                        System.out.print("Enter login: ");
                        String login = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your surname: ");
                        String surname = scanner.nextLine();
                        System.out.print("Enter your gender (true for male, false for female): ");
                        boolean gender = scanner.nextBoolean();

                        boolean registered = userService.registerUser(login, password, name, surname, gender);
                        if (registered) {
                            System.out.println("Registration successful!");
                        } else {
                            System.out.println("Registration failed.");
                        }
                        break;

                    case 2:

                        System.out.print("Enter login: ");
                        login = scanner.nextLine();
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();

                        User user = userService.authenticate(login, password);
                        if (user != null) {
                            System.out.println("Login successful! Welcome, " + user.getName());
                            //2 menu
                            boolean loggedIn = true;
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
                                        // menu of restaurant
                                        System.out.println("Available Restaurants:");
                                        for (Restaurant restaurant : restaurantService.getAllRestaurants()) {
                                            System.out.println("ID: " + restaurant.getId() + ", Name: " + restaurant.getName() +
                                                    ", Location: " + restaurant.getLocation());
                                        }
                                        break;

                                    case 2:
                                        // available tables
                                        System.out.print("Enter restaurant ID to view available tables: ");
                                        int restaurantId = scanner.nextInt();
                                        scanner.nextLine();

                                        System.out.println("Available tables in restaurant " + restaurantId + ":");
                                        for (Table table : tableService.getAvailableTables(restaurantId)) {
                                            System.out.println("Table ID: " + table.getId());
                                        }
                                        break;

                                    case 3:
                                        // reserved
                                        System.out.print("Enter table ID to book: ");
                                        int tableId = scanner.nextInt();
                                        scanner.nextLine();

                                        // Check and reserve
                                        if (bookingService.createBooking(user.getId(), tableId, LocalDateTime.now())) {
                                            System.out.println("");
                                        } else {
                                            System.out.println("");
                                        }
                                        break;


                                    case 4:
                                        // check for reserve
                                        System.out.println("Your Bookings:");
                                        for (Booking booking : bookingService.getBookingsByUserId(user.getId())) {
                                            System.out.println("Booking ID: " + booking.getId() + ", Table ID: " + booking.getTableId() +
                                                    ", Time: " + booking.getBookingTime());
                                        }
                                        break;

                                    case 5:
                                        // exit
                                        loggedIn = false;
                                        System.out.println("Logged out successfully!");
                                        break;

                                    default:
                                        System.out.println("Invalid option. Please try again.");
                                }
                            }
                        } else {
                            System.out.println("Login failed. Please check your credentials.");
                        }
                        break;

                    case 3:
                        //exit
                        System.out.println("Thank you for using Sit&Eat. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
