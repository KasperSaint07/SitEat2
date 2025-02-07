package service;

import Interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private Connection connection;

    private final String url = "jdbc:postgresql://localhost:5432/siteatdb";
    private final String username = "postgres";
    private final String password = "0000";

    // Приватный конструктор
    private PostgresDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database");
    }

    public static PostgresDB getInstance() {
        if (instance == null) {
            try {
                instance = new PostgresDB();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
