package org.deodev.validation;

import org.deodev.dto.request.UserDTO;
import org.deodev.exception.ValidationException;

public class DTOValidator {
    public static void validateUser(UserDTO userDTO) {
        if (userDTO.getName() == null) throw new ValidationException("User name cannot be null");
        if (userDTO.getName().isEmpty()) throw new ValidationException("User name cannot be empty");
        if (userDTO.getEmail() == null) throw new ValidationException("User email cannot be null");
        if (userDTO.getEmail().isEmpty()) throw new ValidationException("User email cannot be empty");
        if (!userDTO.getEmail().contains("@")) throw new ValidationException("Invalid email");
        if (userDTO.getPassword() == null) throw new ValidationException("User password cannot be null");
        if (userDTO.getPassword().isEmpty()) throw new ValidationException("User password cannot be empty");
    }
}
