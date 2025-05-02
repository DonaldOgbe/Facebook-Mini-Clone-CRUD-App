package org.deodev.service;

import org.deodev.dao.UserDAO;
import org.deodev.dto.request.UserDTO;
import org.deodev.exception.ValidationException;
import org.deodev.model.User;
import org.deodev.validation.DTOValidator;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(UserDTO userDto) {
        try {
            DTOValidator.validateUser(userDto);

            if (emailExist(userDto.getEmail())) {
                throw new ValidationException("Email already exists");
            }

            userDto.setPassword(hashPassword(userDto.getPassword()));
            User user = new User(userDto);
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
