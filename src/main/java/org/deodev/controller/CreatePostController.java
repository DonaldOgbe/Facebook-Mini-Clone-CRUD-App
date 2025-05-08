package org.deodev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.request.CreatePostDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.model.User;
import org.deodev.service.PostService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/posts/create")
public class CreatePostController extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

//    /create
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (session == null || session.getAttribute("user") == null) {
                throw new ServletException("Unauthorized user not logged in");
            }

            User user = (User) session.getAttribute("user");

            CreatePostDTO dto = mapper.readValue(request.getReader(), CreatePostDTO.class);

            dto.setUserId(user.getId());
            postService.createPost(dto);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(),
                    Map.of(
                            "message", "Post Created Successfully",
                            "user_id", user.getId(),
                            "email", user.getEmail()
                    )
            );

        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Post", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Post", e.getMessage()));
        }
    }
}



