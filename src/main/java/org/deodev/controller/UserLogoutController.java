package org.deodev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.response.ErrorResponse;

import java.io.IOException;

@WebServlet("/logout")
public class UserLogoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (session == null || session.getAttribute("user") == null) {
                throw new ServletException("Unauthorized user not logged in");
            }

            session.invalidate();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            mapper.writeValue(response.getWriter(), "Logout Successful");
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Logout Failed", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            mapper.writeValue(response.getWriter(), new ErrorResponse("Logout Failed", e.getMessage()));
            e.printStackTrace();
        }
    }
}
