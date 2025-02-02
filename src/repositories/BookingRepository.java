package repositories;

import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final Connection connection;

    public BookingRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean addBooking(int userId, int tableId, Timestamp bookingTime) {
        String sql = "INSERT INTO bookings (user_id, table_id, booking_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tableId);
            stmt.setTimestamp(3, bookingTime);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getBookingsByRestaurantId(int restaurantId) {
        String sql = """
                SELECT b.id, b.user_id, b.table_id, b.booking_time
                FROM bookings b
                JOIN tables t ON b.table_id = t.id
                WHERE t.restaurant_id = ?""";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
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
}
