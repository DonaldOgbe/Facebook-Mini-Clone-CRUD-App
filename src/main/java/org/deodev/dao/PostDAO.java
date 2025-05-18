package org.deodev.dao;

import org.deodev.dto.request.CreatePostDTO;
import org.deodev.model.Post;
import org.deodev.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public Post save(Post post) throws SQLException {
        String query = "INSERT INTO posts (user_id, content) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
        }
        return post;
    }

    public Post getById(int id) throws SQLException {
        String query = "SELECT * FROM posts WHERE id = ?";
        Post post;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        post = parseResultSet(resultSet);
                    } else {
                        throw new SQLException("Failed to extract data from database table");
                    }
                }
            }
        }
        return post;
    }

    public List<Post> getAllPosts() throws SQLException {
        String query = "SELECT * FROM posts";
        List<Post> list = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                try(ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet != null) {
                        while (resultSet.next()) {
                            list.add(parseResultSet(resultSet));
                        }
                    } else {
                        throw new SQLException("No Posts in table to extract");
                    }
                }
            }
        }
        return list;
    }

    public Post updatePost(String content, int postId) throws SQLException {
        Post post;
        String query = "UPDATE posts SET content = ?, updated_at = NOW() WHERE id = ? RETURNING *";;

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, content);
                statement.setInt(2, postId);

                try(ResultSet resultSet = statement.executeQuery()) {
                   if (resultSet.next()) {
                       post = parseResultSet(resultSet);
                   } else {
                       throw new SQLException("No Posts in table by the given ID");
                   }
                }
            }
        }
        return post;
    }

    public void deletePost(int id) throws SQLException {
        String query = "DELETE FROM posts WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    throw  new SQLException("No Posts in table by the given ID");
                }
            }
        }
    }

    public List<Post> findPostsByUserId(int id) throws SQLException {
        List<Post> list = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE user_id = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            list.add(parseResultSet(resultSet));
                        }
                    } else {
                        throw new SQLException("No Posts found with the given user id");
                    }
                }
            }
        }
        return list;
    }

    private Post parseResultSet(ResultSet resultSet) {
        Post post;

        try {
            CreatePostDTO dto = new CreatePostDTO(resultSet.getString("content"),
                    resultSet.getInt("user_id"));

            post = new Post(dto);
            post.setId(resultSet.getInt("id"));
            post.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            post.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return post;
    }

}


