package controllers;
import model.User;
import service.BookingService;
import service.AuthService;
import service.RestaurantService;
import service.TableService;
import java.util.Scanner;
public class MenuManager {
    private AuthService authService;
    private BookingService bookingService;
    private RestaurantService restaurantService;
    private TableService tableService;
    private Scanner scanner=new Scanner(System.in);
    public MenuManager(AuthService authService, BookingService bookingService, RestaurantService restaurantService, TableService tableService) {
        this.authService=authService;
        this.bookingService=bookingService;
        this.restaurantService=restaurantService;
        this.tableService=tableService;
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
        boolean registered = authService.registerUser(login, password, name, surname, gender);
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
                    restaurantService.viewRestaurants();
                    break;
                case 2:
                    System.out.print("Enter restaurant ID to view available tables: ");
                    int restaurantId = scanner.nextInt();
                    scanner.nextLine();
                    tableService.viewAvailableTables(restaurantId);
                    break;
                case 3:
                    System.out.print("Enter table ID to book: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();
                    bookingService.bookTable(user.getId(), tableId);
                        break;
                case4:
                    bookingService.viewUserBookings(user.getId());
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }}
    }
}

