package org.deodev.dao;



import org.deodev.dto.request.UserSignupDTO;
import org.deodev.model.User;
import org.deodev.util.DatabaseUtil;

import java.sql.*;

public class UserDAO {
    public User save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                        if (generatedKey.next()) {
                            user.setId(generatedKey.getInt(1));
                            return user;
                        }
                    }
                }

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        UserSignupDTO userSignupDTO = new UserSignupDTO(
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password")
                        );

                        User user = new User(userSignupDTO);
                        user.setId(resultSet.getInt("id"));
                        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
