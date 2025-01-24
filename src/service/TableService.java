package service;

import model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//All Table logic
public class TableService {
    private final Connection connection;

    public TableService(Connection connection) {
        this.connection = connection;
    }

    // Retrieve available tables for a specific restaurant
    public List<Table> getAvailableTables(int restaurantId) {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM tables WHERE restaurant_id = ? AND is_available = true";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tables.add(new Table(
                        rs.getInt("id"),
                        rs.getInt("restaurant_id"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    // Reserve a table
    public boolean reserveTable(int tableId) {
        String sql = "UPDATE tables SET is_available = false WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a new table
    public boolean addTable(int restaurantId, boolean isAvailable) {
        String sql = "INSERT INTO tables (restaurant_id, is_available) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            stmt.setBoolean(2, isAvailable);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
