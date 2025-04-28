package org.deodev.dao;

import org.deodev.dto.UserDTO;
import org.deodev.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    User testUser;
    UserDTO testUserDTO;
    UserDAO testUserDAO;

    @BeforeEach
    void setup() {
        testUserDTO = new UserDTO("John", "john@email.com", "5463321553214cjdhdud");
        testUser = new User(testUserDTO);
        testUserDAO = new UserDAO();
    }

    @Test
    void save() {
        assertTrue(testUserDAO.save(testUser));
    }

    @Test
    void findUserByEmail() {
        testUserDAO.save(testUser);
        assertNotNull(testUserDAO.findUserByEmail("john@email.com"));
    }
}