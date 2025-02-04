import controllers.MenuManager;
import repositories.*;
import service.*;

public class Main {
    public static void main(String[] args) {        // Initialize database connection
        PostgresDB db = new PostgresDB();
        // Initialize repositories
        AdminRepository adminRepository = new AdminRepository(db.getConnection());
        UserRepository userRepository = new UserRepository(db.getConnection());
        TableRepository tableRepository = new TableRepository(db.getConnection());
        BookingRepository bookingRepository = new BookingRepository(db.getConnection());
        RestaurantRepository restaurantRepository = new RestaurantRepository(db.getConnection());
        // Initialize services
        AuthService authService = new AuthService(adminRepository, userRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        TableService tableService = new TableService(tableRepository);
        BookingService bookingService = new BookingService(bookingRepository);
        UserService userService = new UserService(userRepository);
        // Start menu manager
        MenuManager menuManager = new MenuManager(authService,bookingService,restaurantService,tableService ,userService);
        menuManager.start();
    }}


