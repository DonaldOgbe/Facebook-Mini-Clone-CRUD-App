package org.deodev.dao;


import org.deodev.dto.request.CreateCommentDTO;
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

    public Comment getById(int id) throws SQLException {
        String sql = "SELECT * FROM comments WHERE id = ?";
        CreateCommentDTO dto;
        Comment comment;

        try(Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        dto = new CreateCommentDTO(resultSet.getString("content"),
                                resultSet.getInt("user_id"), resultSet.getInt("post_id"));

                        comment = new Comment(dto);
                        comment.setId(resultSet.getInt("id"));
                        comment.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                        comment.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                    } else {
                        throw new SQLException("Failed to extract data from database table");
                    }
                }
            }
        }

        return comment;
    }
}


