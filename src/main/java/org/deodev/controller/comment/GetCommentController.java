package org.deodev.controller.comment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.exception.ValidationException;
import org.deodev.model.Comment;
import org.deodev.service.CommentService;

import java.io.IOException;

@WebServlet("/comments/*")
public class GetCommentController extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        ObjectMapper mapper = new ObjectMapper();
        int commentId;

        try {

            commentId = Integer.parseInt(pathInfo.substring(1));

            if (commentId < 0) {
                throw new ValidationException("Invalid id parameter");
            }

            Comment comment = commentService.getById(commentId);

            mapper.findAndRegisterModules();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), comment);
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to get comment", e.getMessage()));
        }
    }
}
