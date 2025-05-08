package org.deodev.dao;

import org.deodev.dto.request.CreatePostDTO;
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


    public Post getById(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        CreatePostDTO dto;
        Post post;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        dto = new CreatePostDTO(resultSet.getString("content"),
                                resultSet.getInt("user_id"));

                        post = new Post(dto);
                        post.setId(resultSet.getInt("id"));
                        post.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                        post.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                    } else {
                        throw new SQLException("Failed to extract data from database table");
                    }
                }
            }

        }
        return post;
    }
}


