package org.deodev.dao;

import org.deodev.model.Like;
import org.deodev.util.DatabaseUtil;
import java.sql.*;

public class LikeDAO {

    public Like save(Like like) throws SQLException {
        String sql = "INSERT INTO likes (post_id, comment_id, user_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setObject(1, like.getPostId() == 0 ? null : like.getPostId(), java.sql.Types.INTEGER);
                statement.setObject(2, like.getCommentId() == 0 ? null : like.getCommentId(), java.sql.Types.INTEGER);
                statement.setInt(3, like.getUserId());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Inserting comment failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new SQLException("Failed to get generated keys from database");
                    } like.setId(generatedKeys.getInt(1));
                }
            }
        }
        return like;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM likes WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    throw  new SQLException("No Comments in table by the given ID");
                }
            }
        }
    }
}
