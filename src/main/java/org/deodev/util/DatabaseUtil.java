package org.deodev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/facebook_clone";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}
