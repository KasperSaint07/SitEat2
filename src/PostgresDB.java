import Interfaces.IDB;

import java.sql.*;

public class PostgresDB implements IDB {
    public Connection getConnection() throws SQLException, ClassNotFoundException{
        String url = "jdbc:postgresql://localhost:5432/siteatdb";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, "postgres", "postgres");

            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
