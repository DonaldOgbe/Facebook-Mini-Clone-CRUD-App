package org.deodev.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deodev.dto.request.UserDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.dto.response.SignupResponse;
import org.deodev.model.User;
import org.deodev.service.UserService;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deodev.validation.DTOValidator;

@WebServlet("/signup")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            UserDTO userDTO = mapper.readValue(request.getReader(), UserDTO.class);
            DTOValidator.validateUser(userDTO);

            User user = userService.registerUser(userDTO);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            mapper.writeValue(response.getWriter(), new SignupResponse(user));
        } catch (RuntimeException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CONFLICT);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Registration Failed", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Registration Failed", e.getMessage()));
            e.printStackTrace();
        }
    }
}
