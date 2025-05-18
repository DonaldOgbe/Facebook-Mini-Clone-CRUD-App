package org.deodev.service;

import org.deodev.dao.CommentDAO;
import org.deodev.dao.PostDAO;
import org.deodev.dto.request.CreatePostDTO;
import org.deodev.dto.response.post.GetPostResponseDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.Comment;
import org.deodev.model.Post;
import org.deodev.validation.CreatePostDTOValidator;
import org.deodev.validation.Validator;

import java.sql.SQLException;
import java.util.List;

public class PostService {
    private final PostDAO postDAO;
    private final CommentDAO commentDAO;
    private final Validator<CreatePostDTO> createPostDTOValidator = new CreatePostDTOValidator();

    public PostService() {
        this.postDAO = new PostDAO();
        this.commentDAO = new CommentDAO();
    }

    public Post createPost(CreatePostDTO dto) {
        try {
             createPostDTOValidator.validate(dto);
             Post post = new Post(dto);

             return postDAO.save(post);
        } catch (ValidationException | SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }

    public GetPostResponseDTO findById(int id) {
        try {
            Post post = postDAO.getById(id);
            Comment comment = commentDAO.findByPostId(id);
            int numberOfComments = commentDAO.countCommentsByPostId(id);

            return new GetPostResponseDTO(post, comment, numberOfComments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }

    public List<Post> getAllPosts() {
        try {
            return postDAO.getAllPosts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }

    public Post updatePost(CreatePostDTO dto, int postId) {
        try {
            createPostDTOValidator.validate(dto);
            return postDAO.updatePost(dto.getContent(), postId);
        } catch (ValidationException | SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }

    public void deletePost(int id) {
        try {
            postDAO.deletePost(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }
}
