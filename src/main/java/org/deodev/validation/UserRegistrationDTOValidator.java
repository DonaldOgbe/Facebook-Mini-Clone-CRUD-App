package org.deodev.validation;

import org.deodev.dto.request.UserSignupDTO;
import org.deodev.exception.ValidationException;

public class UserRegistrationDTOValidator implements Validator<UserSignupDTO> {
    public void validate(UserSignupDTO dto) {
        if (dto.getName() == null) throw new ValidationException("User name cannot be null");
        if (dto.getName().isEmpty()) throw new ValidationException("User name cannot be empty");
        if (dto.getEmail() == null) throw new ValidationException("User email cannot be null");
        if (dto.getEmail().isEmpty()) throw new ValidationException("User email cannot be empty");
        if (!dto.getEmail().contains("@")) throw new ValidationException("Invalid email");
        if (dto.getPassword() == null) throw new ValidationException("User password cannot be null");
        if (dto.getPassword().isEmpty()) throw new ValidationException("User password cannot be empty");
    }

}
