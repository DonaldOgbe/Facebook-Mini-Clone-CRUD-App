package org.deodev.service;

import org.deodev.dao.UserDAO;
import org.deodev.dto.request.UserLoginDTO;
import org.deodev.dto.request.UserRegistrationDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.User;
import org.deodev.validation.UserLoginDTOValidator;
import org.deodev.validation.UserRegistrationDTOValidator;
import org.deodev.validation.Validator;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserDAO userDAO;
    private final Validator<UserRegistrationDTO> userRegistrationDTOValidator = new UserRegistrationDTOValidator();
    private final Validator<UserLoginDTO> userLoginDTOValidator = new UserLoginDTOValidator();

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(UserRegistrationDTO dto) {
        try {
            userRegistrationDTOValidator.validate(dto);

            if (emailExist(dto.getEmail())) {
                throw new ValidationException("Email already exists");
            }

            dto.setPassword(hashPassword(dto.getPassword()));
            User user = new User(dto);
            return userDAO.save(user);
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

            User user = userDAO.findUserByEmail(dto.getEmail());

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
        return userDAO.findUserByEmail(email) != null;
    }
}
