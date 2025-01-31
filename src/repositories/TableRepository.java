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
        String sql = "SELECT * FROM tables WHERE restaurant_id = ? AND is_available = true";
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
