package org.deodev.service;

import org.deodev.dao.UserDAO;
import org.deodev.dto.UserDTO;
import org.deodev.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(UserDTO userDto) {
        if (!emailExist(userDto.getEmail())) {
            userDto.setPassword(hashPassword(userDto.getPassword()));
            User user = new User(userDto);

            return userDAO.save(user);
        } else {
            throw new RuntimeException("Email already exists");
        }
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean emailExist(String email) {
        return userDAO.findUserByEmail(email) != null;
    }
}
