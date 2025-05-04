package org.deodev.service;

import org.deodev.dao.UserDAO;
import org.deodev.dto.request.UserRegistrationDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.User;
import org.deodev.validation.UserRegistrationDTOValidator;
import org.deodev.validation.Validator;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserDAO userDAO;
    private Validator<UserRegistrationDTO> dtoValidator = new UserRegistrationDTOValidator();

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(UserRegistrationDTO userRegistrationDto) {
        try {
            dtoValidator.validate(userRegistrationDto);

            if (emailExist(userRegistrationDto.getEmail())) {
                throw new ValidationException("Email already exists");
            }

            userRegistrationDto.setPassword(hashPassword(userRegistrationDto.getPassword()));
            User user = new User(userRegistrationDto);
            return userDAO.save(user);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("User registration failed", e);
        }
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean emailExist(String email) {
        return userDAO.findUserByEmail(email) != null;
    }
}
