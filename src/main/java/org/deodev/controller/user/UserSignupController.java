package org.deodev.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deodev.dto.request.UserSignupDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.model.User;
import org.deodev.service.AuthService;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/signup")
public class UserSignupController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            UserSignupDTO userSignupDTO = mapper.readValue(request.getReader(), UserSignupDTO.class);

            User user = authService.registerUser(userSignupDTO);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);

            mapper.writeValue(response.getWriter(),
                    Map.of("message", "Signup Successful",
                            "data", user.getId()));
        } catch (RuntimeException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CONFLICT);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Signup Failed", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Signup Failed", e.getMessage()));
            e.printStackTrace();
        }
    }
}
