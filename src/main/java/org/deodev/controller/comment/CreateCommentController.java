package org.deodev.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.request.CreateCommentDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.exception.ValidationException;
import org.deodev.model.Comment;
import org.deodev.model.User;
import org.deodev.service.CommentService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/comments/create/*")
public class CreateCommentController extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

            if (postId < 0) {
                throw new ValidationException("Invalid id parameter");
            }

            User user = (User) session.getAttribute("user");

            CreateCommentDTO dto = mapper.readValue(request.getReader(), CreateCommentDTO.class);

            dto.setUserId(user.getId());
            dto.setPostId(postId);
            Comment comment = commentService.createComment(dto);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(response.getWriter(),
                    Map.of(
                            "message", "Comment Created Successfully",
                            "user_id", user.getId(),
                            "email", user.getEmail(),
                            "post_id", comment.getPostId()
                    )
            );

        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Comment", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Comment", e.getMessage()));
        }

    }
}
