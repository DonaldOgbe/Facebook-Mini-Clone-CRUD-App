package org.deodev.controller.like;

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
import org.deodev.service.LikeService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/likes/delete/*")
public class DeleteLikeController extends HttpServlet {

    private LikeService likeService;

    @Override
    public void init() throws ServletException {
        this.likeService = new LikeService();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        int likeId;

        try {
            if (session == null || session.getAttribute("user") == null) {
                throw new ServletException("Unauthorized user not logged in");
            }

            likeId = Integer.parseInt(pathInfo.substring(1));

            if (likeId < 0) {
                throw new ValidationException("Invalid id parameter");
            }

            likeService.delete(likeId);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), new GenericApiResponse<>("Like Deleted Successfully", Map.of("id", likeId)));
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Like", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to delete Like", e.getMessage()));
        }
    }
}
