package org.deodev.service;

import org.deodev.dao.UserDAO;
import org.deodev.dto.request.UserLoginDTO;
import org.deodev.dto.request.UserSignupDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.User;
import org.deodev.validation.UserLoginDTOValidator;
import org.deodev.validation.UserRegistrationDTOValidator;
import org.deodev.validation.Validator;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO dao;
    private final Validator<UserSignupDTO> userRegistrationDTOValidator = new UserRegistrationDTOValidator();
    private final Validator<UserLoginDTO> userLoginDTOValidator = new UserLoginDTOValidator();

    public AuthService() {
        this.dao = new UserDAO();
    }

    public User registerUser(UserSignupDTO dto) {
        try {
            userRegistrationDTOValidator.validate(dto);

            if (emailExist(dto.getEmail())) {
                throw new ValidationException("Email already exists");
            }

            dto.setPassword(hashPassword(dto.getPassword()));
            User user = new User(dto);

            return dao.save(user);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Auth Service Error", e);
        }
    }

    public User login(UserLoginDTO dto) {
        try {
            userLoginDTOValidator.validate(dto);

            if (!emailExist(dto.getEmail())) {
                throw new ValidationException("Email does not exist");
            }

            User user = dao.findUserByEmail(dto.getEmail());

            if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
                throw new ValidationException("Incorrect Password");
            };

            return user;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Auth Service Error", e);
        }
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean emailExist(String email) {
        boolean exists;
        try {
           exists = dao.emailExist(email);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return exists;
    }
}
