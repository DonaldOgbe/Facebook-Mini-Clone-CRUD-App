package org.deodev.dao;



import org.deodev.dto.request.UserSignupDTO;
import org.deodev.model.User;
import org.deodev.util.DatabaseUtil;
import java.sql.*;

public class UserDAO {
    public User save(User user) throws SQLException{
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

                if (rowsAffected ==  0) {
                    throw new SQLException("Failed to update tables in database");
                }

                try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                    if (!generatedKey.next()) {
                        throw new SQLException("Failed to get generated keys from database");
                    }
                    user.setId(generatedKey.getInt(1));
                }
            }
        }
        return user;
    }

    public User findUserByEmail(String email) throws SQLException {
        User user;
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

                        user = new User(userSignupDTO);
                        user.setId(resultSet.getInt("id"));
                        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                    } else {
                        throw new SQLException("No User found with the given email");
                    }
                }
            }
        }
        return user;
    }

    public boolean emailExist(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        boolean exists;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);

                try (ResultSet resultSet = statement.executeQuery()) {
                    exists = resultSet.next();
                }
            }
        }
        return exists;
    }
}
