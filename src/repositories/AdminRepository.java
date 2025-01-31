package repositories;

import model.Admin;
import java.sql.*;

public class AdminRepository {
    private final Connection connection;

    public AdminRepository(Connection connection) {
        this.connection = connection;
    }

    public Admin getAdminByCredentials(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("restaurant_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
