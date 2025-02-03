package service;

import model.Admin;
import model.Booking;
import model.Table;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.TableRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository;
    private final TableRepository tableRepository;
    private final BookingRepository bookingRepository;

    public AdminService(AdminRepository adminRepository, TableRepository tableRepository, BookingRepository bookingRepository) {
        this.adminRepository = adminRepository;
        this.tableRepository = tableRepository;
        this.bookingRepository = bookingRepository;
    }

    // Получить информацию об администраторе по учетным данным
    public Admin getAdminByCredentials(String username, String password) {
        return adminRepository.getAdminByCredentials(username, password);
    }

    // Получить все бронирования для конкретного ресторана
    public List<Booking> getBookingsForRestaurant(int restaurantId) {
        return bookingRepository.getBookingsByRestaurantId(restaurantId);
    }

    // Добавить новый столик в ресторан
    public boolean addTable(int restaurantId) {
        return tableRepository.addTable(restaurantId);
    }

    // Удалить столик из ресторана
    public boolean removeTable(int tableId) {
        return tableRepository.removeTable(tableId);
    }

    // Получить список всех столиков в ресторане
    public List<Table> getTablesByRestaurant(int restaurantId) {
        return tableRepository.getTablesByRestaurant(restaurantId);
    }

    // Изменить доступность столика
    public boolean updateTableAvailability(int tableId, boolean isAvailable) {
        return tableRepository.updateTableAvailability(tableId, isAvailable);
    }
}
