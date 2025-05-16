package org.deodev.controller.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.deodev.dto.request.CreateLikeDTO;
import org.deodev.dto.response.ErrorResponse;
import org.deodev.model.Like;
import org.deodev.model.User;
import org.deodev.service.LikeService;
import java.io.IOException;
import java.util.Map;

@WebServlet("/likes/create")
public class CreateLikeController extends HttpServlet {

    private LikeService likeService;

    @Override
    public void init() throws ServletException {
        this.likeService = new LikeService();
    }

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
            CreateLikeDTO dto = mapper.readValue(request.getReader(), CreateLikeDTO.class);
            dto.setUserId(user.getId());
            Like like = likeService.save(dto);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(response.getWriter(),
                    Map.of(
                            "message", "Like Created Successfully",
                            "user_id", user.getId(),
                            "email", user.getEmail(),
                            "post_id", like.getPostId(),
                            "commentId", like.getCommentId()
                    )
            );
        } catch (ServletException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Like", e.getMessage()));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(), new ErrorResponse("Failed to create Like", e.getMessage()));
        }
    }
}
