package org.deodev.dao;


import org.deodev.model.Comment;
import org.deodev.util.DatabaseUtil;

import java.sql.*;

public class CommentDAO {
    public Comment save(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, comment.getPostId());
                statement.setInt(2, comment.getUserId());
                statement.setString(3, comment.getContent());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected ==  0) {
                    throw new SQLException("Inserting comment failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new SQLException("Failed to get generated keys from database");
                    }
                    comment.setId(generatedKeys.getInt(1));
                }
            }
        }

        return comment;
    }
}
