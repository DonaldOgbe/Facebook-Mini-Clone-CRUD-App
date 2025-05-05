package org.deodev.dao;

import org.deodev.model.Post;
import org.deodev.util.DatabaseUtil;

import java.sql.*;

public class PostDAO {

    public Post save(Post post) throws SQLException {
        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, post.getUserId());
                statement.setString(2, post.getContent());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected ==  0) {
                    throw new SQLException("Failed to update tables in database");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new SQLException("Failed to get generated keys from database");
                    }
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }
}
