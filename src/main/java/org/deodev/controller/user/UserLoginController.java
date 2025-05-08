package org.deodev.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.request.UserLoginDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.model.User;
import org.deodev.service.AuthService;

import java.io.IOException;

@WebServlet("/login")
public class UserLoginController extends HttpServlet {
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
            UserLoginDTO dto = mapper.readValue(request.getReader(), UserLoginDTO.class);

            User user = authService.login(dto);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            mapper.writeValue(response.getWriter(), "message: Login Successful");

        } catch (RuntimeException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Login Failed", e.getMessage()));
        }  catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Login Failed", e.getMessage()));
            e.printStackTrace();
        }
    }
}
