package org.deodev.service;

import org.deodev.dao.PostDAO;
import org.deodev.dto.request.CreatePostDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.Post;
import org.deodev.validation.CreatePostDTOValidator;
import org.deodev.validation.Validator;

public class PostService {
    private final PostDAO dao;
    private final Validator<CreatePostDTO> createPostDTOValidator = new CreatePostDTOValidator();

    public PostService() {
        this.dao = new PostDAO();
    }

    public Post createPost(CreatePostDTO dto) {
        try {
             createPostDTOValidator.validate(dto);
             Post post = new Post(dto);

             return dao.save(post);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Post Service Error", e);
        }
    }


}
