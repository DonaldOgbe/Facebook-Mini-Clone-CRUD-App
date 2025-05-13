package org.deodev.validation;

import org.deodev.dto.request.CreateCommentDTO;

import org.deodev.exception.ValidationException;

public class CreateCommentDTOValidator implements Validator<CreateCommentDTO>{
    @Override
    public void validate(CreateCommentDTO dto) {
        if (dto.getContent() == null) throw new ValidationException("Comment content cannot be null");
        if (dto.getContent().isEmpty()) throw new ValidationException("Comment content cannot be empty");
    }
}

