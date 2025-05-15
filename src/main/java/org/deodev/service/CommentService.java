package org.deodev.service;

import org.deodev.dao.CommentDAO;
import org.deodev.dto.request.CreateCommentDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.Comment;
import org.deodev.validation.CreateCommentDTOValidator;
import org.deodev.validation.Validator;
import java.sql.SQLException;
import java.util.List;

public class CommentService {

    private final CommentDAO dao;
    private final Validator<CreateCommentDTO> commentDTOValidator = new CreateCommentDTOValidator();

    public CommentService() {
        this.dao = new CommentDAO();
    }

    public Comment createComment(CreateCommentDTO dto) {
        try {
            commentDTOValidator.validate(dto);
            Comment comment = new Comment(dto);

            return dao.save(comment);
        } catch (ValidationException | SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }

    public Comment getById(int id) {
        try {
            return dao.getById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }

    public List<Comment> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }

    public Comment update(String content, int id) {
        try {
            return dao.update(content, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }

    public void delete(int id) {
        try {
            dao.deletePost(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }

}
