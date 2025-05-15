package org.deodev.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.dto.response.GenericApiResponse;
import org.deodev.exception.ValidationException;
import org.deodev.service.CommentService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/comments/delete/*")
public class DeleteCommentController extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        int commentId;

        try {
            if (session == null || session.getAttribute("user") == null) {
                throw new ServletException("Unauthorized user not logged in");
            }

            commentId = Integer.parseInt(pathInfo.substring(1));

            if (commentId < 0) {
                throw new ValidationException("Invalid id parameter");
            }

            commentService.delete(commentId);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("Comment Deleted Successfully", Map.of("id", commentId)));
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Comment", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Comment", e.getMessage()));
        }
    }
}
