//package org.deodev.controller.comment;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.deodev.dto.request.CreateCommentDTO;
//import org.deodev.dto.response.ErrorResponse;
//import org.deodev.dto.response.GenericApiResponse;
//import org.deodev.exception.ValidationException;
//import org.deodev.model.Comment;
//import org.deodev.service.CommentService;
//
//import java.io.IOException;
//
//@WebServlet("comments/update/*")
//public class UpdateCommentController extends HttpServlet {
//
//    private CommentService commentService;
//
//    @Override
//    public void init() throws ServletException {
//        commentService = new CommentService();
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String pathInfo = request.getPathInfo();
//        HttpSession session = request.getSession(false);
//        ObjectMapper mapper = new ObjectMapper();
//        int commentId;
//
//
//        try {
//            if (session == null || session.getAttribute("user") == null) {
//                throw new ServletException("Unauthorized user not logged in");
//            }
//
//            commentId = Integer.parseInt(pathInfo.substring(1));
//
//            if (commentId < 0) {
//                throw new ValidationException("Invalid id parameter");
//            }
//
//            CreateCommentDTO dto = mapper.readValue(request.getReader(), CreateCommentDTO.class);
//            Comment comment = commentService.update(dto.getContent(), commentId);
//
//            mapper.findAndRegisterModules();
//            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_OK);
//            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("Comment Updated Successfully", comment));
//        } catch (ServletException e) {
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to update Comment", e.getMessage()));
//        } catch (Exception e) {
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to update Comment", e.getMessage()));
//        }
//    }
//}
