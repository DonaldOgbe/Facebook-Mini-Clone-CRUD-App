package org.deodev.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.dto.response.GenericApiResponse;
import org.deodev.model.Post;
import org.deodev.service.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet("/posts")
public class GetAllPostsController extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Post> postList = postService.getAllPosts();


            mapper.findAndRegisterModules();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("GET posts Success", postList));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Post", e.getMessage()));
        }
    }
}
