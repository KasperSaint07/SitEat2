import Interfaces.IDB;
import repositories.*;
import service.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем подключение к базе данных
            IDB db = new PostgresDB();
            var connection = db.getConnection();

            // Создаем экземпляры репозиториев
            UserRepository userRepository = new UserRepository(connection);
            RestaurantRepository restaurantRepository = new RestaurantRepository(connection);
            TableRepository tableRepository = new TableRepository(connection);
            BookingRepository bookingRepository = new BookingRepository(connection);
            AdminRepository adminRepository = new AdminRepository(connection);

            // Создаем экземпляры сервисов
            UserService userService = new UserService(userRepository);
            RestaurantService restaurantService = new RestaurantService(restaurantRepository);
            TableService tableService = new TableService(tableRepository);
            BookingService bookingService = new BookingService(bookingRepository);
            AdminService adminService = new AdminService(adminRepository, tableRepository, bookingRepository);

            // TODO: добавьте логику для взаимодействия с пользователем (например, MyApplication)
            System.out.println("Setup completed successfully. Add menu logic here.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
