package service;

import model.Booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// All logic related to booking
public class BookingService {
    private final Connection connection;

    public BookingService(Connection connection) {
        this.connection = connection;
    }

    // Create a new booking
    public boolean createBooking(int userId, int tableId, LocalDateTime bookingTime) {
        // Check if the table is available
        if (!isTableAvailable(tableId)) {
            System.out.println(" ");
            System.out.println("Table " + tableId + " is already reserved.");
            return false; // The table is occupied, booking is not possible
        }

        String sql = "INSERT INTO bookings (user_id, table_id, booking_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tableId);
            stmt.setTimestamp(3, Timestamp.valueOf(bookingTime));

            // Update the table status to "occupied" only if the booking is successful
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
        return false; // Error occurred while booking
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

    // Reserve a table
    private void reserveTable(int tableId) {
        String sql = "UPDATE tables SET is_available = false WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all bookings for a specific user
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
            // Get table_id from the booking before deleting
            int tableId = getTableIdByBookingId(bookingId);
            if (tableId > 0) {
                stmt.setInt(1, bookingId);
                boolean success = stmt.executeUpdate() > 0;
                if (success) {
                    // Make the table available
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
