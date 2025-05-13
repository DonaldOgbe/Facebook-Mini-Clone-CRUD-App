package org.deodev.service;

import org.deodev.dao.CommentDAO;
import org.deodev.dto.request.CreateCommentDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.Comment;
import org.deodev.validation.CreateCommentDTOValidator;
import org.deodev.validation.Validator;

import java.sql.SQLException;

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
            System.out.println("Comment(service): " + comment.getContent());

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
        } catch (ValidationException | SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Comment Service Error", e);
        }
    }
}
