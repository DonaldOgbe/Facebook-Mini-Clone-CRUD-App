package org.deodev.dao;



import org.deodev.dto.UserDTO;
import org.deodev.model.User;
import org.deodev.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean save(User user) {

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try(Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection != null ? connection.prepareStatement(sql) : null;) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try(Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection != null ? connection.prepareStatement(sql) : null;) {

            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    UserDTO userDTO= new UserDTO(resultSet.getString("name"),
                                                    resultSet.getString("email"),
                                                    resultSet.getString("password"));
                    User user = new User(userDTO);
                    user.setId(resultSet.getInt("id"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
