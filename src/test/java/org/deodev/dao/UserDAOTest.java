package org.deodev.dao;

import org.deodev.dto.request.UserSignupDTO;
import org.deodev.model.User;
import org.deodev.validation.UserRegistrationDTOValidator;
import org.deodev.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    User testUser;
    UserSignupDTO testUserSignupDTO;
    UserDAO testUserDAO;
    Validator<UserSignupDTO> dtoValidator = new UserRegistrationDTOValidator();

    @BeforeEach
    void setup() {
        testUserSignupDTO = new UserSignupDTO("John", "john@email.com", "5463321553214cjdhdud");
        dtoValidator.validate(testUserSignupDTO);
        testUser = new User(testUserSignupDTO);
        testUserDAO = new UserDAO();
    }

    @Test
    void save() {
        assertNotNull(testUserDAO.save(testUser));
    }

    @Test
    void findUserByEmail() {
        testUserDAO.save(testUser);
        assertNotNull(testUserDAO.findUserByEmail("john@email.com"));
    }
}