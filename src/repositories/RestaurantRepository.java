package repositories;

import model.Restaurant;
import repositories.interfaces.IRestaurantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository implements IRestaurantRepository {
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
                        rs.getString("location"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Restaurant(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

