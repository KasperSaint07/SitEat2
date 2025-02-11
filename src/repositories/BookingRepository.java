package repositories;

import model.Booking;
import repositories.interfaces.IBookingRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final Connection connection;

    public BookingRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addBooking(int userId, int tableId, LocalDateTime bookingTime, LocalDateTime bookingEndTime) {
        String sql = "INSERT INTO bookings (user_id, table_id, booking_time, booking_end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tableId);
            stmt.setTimestamp(3, Timestamp.valueOf(bookingTime));
            stmt.setTimestamp(4, Timestamp.valueOf(bookingEndTime));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByRestaurantId(int restaurantId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
                SELECT b.id, b.user_id, b.table_id, b.booking_time, b.booking_end_time
                FROM bookings b
                JOIN tables t ON b.table_id = t.id
                WHERE t.restaurant_id = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime(),
                        rs.getTimestamp("booking_end_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Получаем время начала брони (предполагается, что оно всегда заполнено)
                LocalDateTime bookingTime = rs.getTimestamp("booking_time").toLocalDateTime();

                // Получаем время окончания брони, проверяя на null
                Timestamp tsEnd = rs.getTimestamp("booking_end_time");
                LocalDateTime bookingEndTime = (tsEnd != null) ? tsEnd.toLocalDateTime() : null;

                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        bookingTime,
                        bookingEndTime
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDateTime bookingTime = rs.getTimestamp("booking_time").toLocalDateTime();
                Timestamp tsEnd = rs.getTimestamp("booking_end_time");
                LocalDateTime bookingEndTime = (tsEnd != null) ? tsEnd.toLocalDateTime() : null;

                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        bookingTime,
                        bookingEndTime
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

}
