package repositories;

import model.Restaurant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    private final Connection connection;

    public RestaurantRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants ORDER BY id ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                restaurants.add(new Restaurant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }





    public boolean addRestaurant(String name, String location) {
        String sql = "INSERT INTO restaurants (name, location) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, location);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRestaurant(int restaurantId) {
        String sql = "DELETE FROM restaurants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Restaurant(rs.getInt("id"), rs.getString("name"), rs.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
