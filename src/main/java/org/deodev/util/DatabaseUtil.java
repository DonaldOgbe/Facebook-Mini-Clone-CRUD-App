package org.deodev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private final String URL = "jdbc:postgresql://localhost:5432/facebook_clone";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "password";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}
