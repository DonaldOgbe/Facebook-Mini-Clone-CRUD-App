package org.deodev.dao;

import org.deodev.dto.request.CreatePostDTO;
import org.deodev.model.Post;
import org.deodev.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        dto = new CreatePostDTO(resultSet.getInt("id"), resultSet.getString("content"),
                                resultSet.getInt("user_id"));

                        post = new Post(dto);
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

    public List<Post> getAllPosts() throws SQLException {
        String sql = "SELECT * FROM posts";
        List<Post> postList = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet != null) {
                        while (resultSet.next()) {
                            CreatePostDTO dto = new CreatePostDTO(resultSet.getInt("id"), resultSet.getString("content"),
                                    resultSet.getInt("user_id"));

                            Post post = new Post(dto);
                            post.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                            post.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                            postList.add(post);
                        }
                    } else {
                        throw new SQLException("No Posts in table to extract");
                    }
                }
            }
        }
        return postList;
    }

    public Post updatePost(Post post) throws SQLException {
        Post updatedPost;
        String sql = "UPDATE posts SET content = ?, updated_at = NOW() WHERE id = ? RETURNING *";;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, post.getContent());
                statement.setInt(2, post.getId());


                try(ResultSet resultSet = statement.executeQuery()) {
                   if (resultSet.next()) {
                       CreatePostDTO dto = new CreatePostDTO(resultSet.getInt("id"),
                               resultSet.getString("content"),
                               resultSet.getInt("user_id"));

                       updatedPost = new Post(dto);
                       updatedPost.setId(resultSet.getInt("id"));
                       updatedPost.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                       updatedPost.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                   } else {
                       throw new SQLException("No Posts in table by the given ID");
                   }
                }
            }
        }

        return updatedPost;
    }

    public void deletePost(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    throw  new SQLException("No Posts in table by the given ID");
                }
            }
        }
    }

}


