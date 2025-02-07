import controllers.MainMenuController;
import controllers.MenuManager;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.RestaurantRepository;
import repositories.TableRepository;
import repositories.UserRepository;
import service.AuthService;
import service.BookingService;
import service.RestaurantService;
import service.TableService;
import service.UserService;
import service.PostgresDB;
import java.util.logging.LogManager;
import java.sql.Connection;

public class Application {
    private final MainMenuController mainMenuController;

    public Application() {
        // Отключает все логи java.util.logging
        LogManager.getLogManager().reset();
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

        // Создаем MenuManager для пользовательской части
        MenuManager menuManager = new MenuManager(authService, bookingService, restaurantService, tableService, userService);
        // Создаем MainMenuController, которому передаем все сервисы и menuManager
        mainMenuController = new MainMenuController(authService, bookingService, restaurantService, tableService, userService, menuManager);
    }

    public void start() {
        mainMenuController.start();
    }
}