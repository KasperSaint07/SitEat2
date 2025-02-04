package repositories;

import model.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableRepository {
    private final Connection connection;

    public TableRepository(Connection connection) {
        this.connection = connection;
    }
    public List<Table> getAvailableTables(int restaurantId) {
        List<Table> tables = new ArrayList<>();
        String sql = """ 
            SELECT t.id, t.restaurant_id, t.is_available  
            FROM tables t 
            LEFT JOIN bookings b ON t.id = b.table_id 
            WHERE t.restaurant_id = ? AND t.is_available = true AND b.table_id IS NULL 
            LIMIT 10 
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tables.add(new Table(rs.getInt("id"), rs.getInt("restaurant_id"), rs.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
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

    public boolean updateTableAvailability(int tableId, boolean isAvailable) {
        String sql = "UPDATE tables SET is_available = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, tableId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
