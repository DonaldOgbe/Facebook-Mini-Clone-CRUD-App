package org.deodev.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.dto.response.GenericApiResponse;
import org.deodev.service.PostService;

import java.io.IOException;
import java.util.Map;


@WebServlet("/posts/delete/*")
public class DeletePostController extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        int postId;

        try {
            if (session == null || session.getAttribute("user") == null) {
                throw new ServletException("Unauthorized user not logged in");
            }

            postId = Integer.parseInt(pathInfo.substring(1));
            postService.deletePost(postId);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("Post Deleted Successfully", Map.of("id", postId)));
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Post", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Post", e.getMessage()));
        }
    }
}
