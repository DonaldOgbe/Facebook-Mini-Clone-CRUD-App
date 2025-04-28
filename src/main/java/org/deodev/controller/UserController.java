package org.deodev.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deodev.dto.UserDTO;
import org.deodev.service.UserService;
import java.io.IOException;

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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean success = userService.registerUser(new UserDTO(name, email, password));

        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("error", "Signup failed. Please try again.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }

    }
}
