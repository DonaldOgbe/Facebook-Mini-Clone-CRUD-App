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

    public void registerUser(UserDTO userDto) {
        userDto.setPassword(hashPassword(userDto.getPassword()));
        User user = new User(userDto);

        boolean registered = userDAO.save(user);

        if (!registered) throw new RuntimeException("Registration failed");
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean doesEmailExist(String email) {
        return true;
    }
}
