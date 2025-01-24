package service;

import model.Booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing bookings.
 */
public class BookingService {
    private final Connection connection;

    public BookingService(Connection connection) {
        this.connection = connection;
    }

    // Create a new booking
    // Create a new booking
    public boolean createBooking(int userId, int tableId, LocalDateTime bookingTime) {
        // Проверяем, доступен ли столик
        if (!isTableAvailable(tableId)) {
            System.out.println(" ");
            System.out.println("Table " + tableId + " is already reserved.");
            return false; // Столик занят, бронирование невозможно
        }

        String sql = "INSERT INTO bookings (user_id, table_id, booking_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tableId);
            stmt.setTimestamp(3, Timestamp.valueOf(bookingTime));

            // Обновляем статус столика на "занят" только если бронирование успешно
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                reserveTable(tableId);
                System.out.println(" ");
                System.out.println("Table booked successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Ошибка при выполнении бронирования
    }


    // Check if a table is available
    public boolean isTableAvailable(int tableId) {
        String sql = "SELECT is_available FROM tables WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Reserve a table (set is_available = false)
    private void reserveTable(int tableId) {
        String sql = "UPDATE tables SET is_available = false WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all bookings for a specific user
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Cancel a booking and make the table available again
    public boolean cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Получаем table_id из бронирования перед удалением
            int tableId = getTableIdByBookingId(bookingId);
            if (tableId > 0) {
                stmt.setInt(1, bookingId);
                boolean success = stmt.executeUpdate() > 0;
                if (success) {
                    // Освобождаем столик
                    makeTableAvailable(tableId);
                }
                return success;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get table ID by booking ID
    private int getTableIdByBookingId(int bookingId) {
        String sql = "SELECT table_id FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("table_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Make a table available (set is_available = true)
    private void makeTableAvailable(int tableId) {
        String sql = "UPDATE tables SET is_available = true WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
