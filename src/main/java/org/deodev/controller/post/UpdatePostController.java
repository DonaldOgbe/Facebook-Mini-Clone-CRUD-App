package org.deodev.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.request.CreatePostDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.dto.response.GenericApiResponse;
import org.deodev.model.Post;
import org.deodev.service.PostService;

import java.io.IOException;


@WebServlet("/posts/update/*")
public class UpdatePostController extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
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

            CreatePostDTO dto = mapper.readValue(request.getReader(), CreatePostDTO.class);
            dto.setId(postId);
            Post post = postService.updatePost(dto);

            mapper.findAndRegisterModules();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("Post Updated Successfully", post));
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to update Post", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to update Post", e.getMessage()));
        }

    }

}
