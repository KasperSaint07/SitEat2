package repositories;

import model.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import repositories.interfaces.ITableRepository;

public class TableRepository implements ITableRepository {
    private final Connection connection;

    public TableRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<Table> getTablesByRestaurant(int restaurantId) {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM tables WHERE restaurant_id = ?";
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

    @Override
    public boolean addTable(int restaurantId) {
        String sql = "INSERT INTO tables (restaurant_id, is_available) VALUES (?, true)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeTable(int tableId) {
        String sql = "DELETE FROM tables WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getRestaurantIdByTable(int tableId) {
        String sql = "SELECT restaurant_id FROM tables WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tableId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("restaurant_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если ресторан не найден
    }
}

