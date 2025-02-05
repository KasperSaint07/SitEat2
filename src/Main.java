import java.sql.Connection;
import java.util.Scanner;

// Импортируйте необходимые классы из ваших пакетов
import controllers.MenuManager;
import controllers.AdminMenuManager;
import model.Admin;
import model.User;
import repositories.AdminRepository;
import repositories.UserRepository;
import repositories.TableRepository;
import repositories.BookingRepository;
import repositories.RestaurantRepository;
import service.AuthService;
import service.RestaurantService;
import service.TableService;
import service.BookingService;
import service.UserService;
import service.PostgresDB;

public class Main {
    public static void main(String[] args) {
        // Инициализация подключения к базе данных
        PostgresDB db = new PostgresDB();
        Connection connection = db.getConnection();

        // Инициализация репозиториев
        AdminRepository adminRepository = new AdminRepository(connection);
        UserRepository userRepository = new UserRepository(connection);
        TableRepository tableRepository = new TableRepository(connection);
        BookingRepository bookingRepository = new BookingRepository(connection);
        RestaurantRepository restaurantRepository = new RestaurantRepository(connection);

        // Инициализация сервисов
        AuthService authService = new AuthService(adminRepository, userRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        TableService tableService = new TableService(tableRepository);
        BookingService bookingService = new BookingService(bookingRepository, tableRepository);
        UserService userService = new UserService(userRepository);

        // Главное меню для выбора типа входа
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\nSelect login type:");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Пользовательский вход
                    System.out.print("Enter user login: ");
                    String userLogin = scanner.nextLine();
                    System.out.print("Enter user password: ");
                    String userPassword = scanner.nextLine();
                    User user = authService.loginAsUser(userLogin, userPassword);
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + user.getName());
                        // Передаём управление в пользовательское меню
                        MenuManager menuManager = new MenuManager(authService, bookingService, restaurantService, tableService, userService);
                        menuManager.start();
                    } else {
                        System.out.println("Invalid user credentials.");
                    }
                    break;
                case 2:
                    // Вход администратора
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    Admin admin = authService.loginAsAdmin(adminUsername, adminPassword);
                    if (admin != null) {
                        System.out.println("Login successful! Welcome, admin " + admin.getUsername());
                        // Передаём управление в меню администратора
                        AdminMenuManager adminMenuManager = new AdminMenuManager(admin, bookingService, tableService, restaurantService, userService);
                        adminMenuManager.start();
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
