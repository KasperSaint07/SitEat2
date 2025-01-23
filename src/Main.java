import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/siteatdb";
        try {
            Connection con = DriverManager.getConnection(url, "postgres", "0000");
            System.out.println("Connected");

        } catch (SQLException e) {
            System.out.println("Exception occurred!");
            e.printStackTrace();
        }
    }
}