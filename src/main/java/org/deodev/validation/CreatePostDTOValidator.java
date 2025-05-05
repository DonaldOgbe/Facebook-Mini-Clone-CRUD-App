package org.deodev.validation;

import org.deodev.dto.request.CreatePostDTO;
import org.deodev.exception.ValidationException;

public class CreatePostDTOValidator implements Validator<CreatePostDTO>{
    @Override
    public void validate(CreatePostDTO dto) {
        if (dto.getContent() == null) throw new ValidationException("Post content cannot be null");
        if (dto.getContent().isEmpty()) throw new ValidationException("Post content cannot be empty");
    }
}
