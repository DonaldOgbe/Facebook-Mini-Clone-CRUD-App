package org.deodev.dao;

import org.deodev.dto.request.CreateCommentDTO;
import org.deodev.model.Comment;
import org.deodev.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        Comment comment;

        try(Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        comment = parseResultSet(resultSet);
                    } else {
                        throw new SQLException("Failed to extract data from database table");
                    }
                }
            }
        }
        return comment;
    }

    public List<Comment> getAll() throws SQLException {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * FROM comments";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet != null) {
                        while (resultSet.next()) {
                            Comment comment = parseResultSet(resultSet);

                            list.add(comment);
                        }
                    } else {
                        throw new SQLException("No Comments in table to extract");
                    }
                }
            }
        }
        return list;
    }

    public Comment update(String content, int commentId) throws SQLException {
        Comment comment;
        String sql = "UPDATE comments SET content = ?, updated_at = NOW() WHERE id = ? RETURNING *";;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, content);
                statement.setInt(2, commentId);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        comment = parseResultSet(resultSet);
                    } else {
                        throw new SQLException("No Comments in table by the given ID");
                    }
                }
            }
        }
        return comment;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM comments WHERE id = ?";

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

    private Comment parseResultSet(ResultSet resultSet) {
        Comment comment;

        try {
            CreateCommentDTO dto = new CreateCommentDTO(resultSet.getString("content"),
                    resultSet.getInt("user_id"), resultSet.getInt("post_id"));
            comment = new Comment(dto);
            comment.setId(resultSet.getInt("id"));
            comment.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            comment.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return  comment;
    }
}


