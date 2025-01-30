package repositories;
import model.Admin;
import java.sql.*;

public class AdminRepository {
    private final Connection connection;
    public AdminRepository() {
        this.connection=connection;
    }
    public Admin getAdminByCrediantals(String username, String password) {
        String SQL="SELECT * FROM admin WHERE username=? AND password=?";
        try (PreparedStatement stmt=connection.prepareStatement(SQL)) {
        stmt.setString(1,username);
        stmt.setString(2,username);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            return new Admin(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getInt("restaraunt_id"))
        } catch(SQLException e){
            e.printStackTrace();
            }
        return null;
        }
    }
}
