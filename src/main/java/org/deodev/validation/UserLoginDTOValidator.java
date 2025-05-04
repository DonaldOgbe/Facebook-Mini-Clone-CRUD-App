package org.deodev.validation;

import org.deodev.dto.request.UserLoginDTO;
import org.deodev.exception.ValidationException;

public class UserLoginDTOValidator implements Validator<UserLoginDTO> {
    @Override
    public void validate(UserLoginDTO dto) {
        if (dto.getEmail() == null) throw new ValidationException("User email cannot be null");
        if (dto.getEmail().isEmpty()) throw new ValidationException("User email cannot be empty");
        if (!dto.getEmail().contains("@")) throw new ValidationException("Invalid email");
        if (dto.getPassword() == null) throw new ValidationException("User password cannot be null");
        if (dto.getPassword().isEmpty()) throw new ValidationException("User password cannot be empty");
    }
}
