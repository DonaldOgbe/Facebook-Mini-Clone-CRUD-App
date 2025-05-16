package org.deodev.service;

import org.deodev.dao.LikeDAO;
import org.deodev.dto.request.CreateLikeDTO;
import org.deodev.model.Like;

import java.sql.SQLException;

public class LikeService {

    private final LikeDAO dao;

    public LikeService() {
        this.dao = new LikeDAO();
    }

    public Like save(CreateLikeDTO dto) {
        try {
            Like like = new Like(dto);

            return dao.save(like);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Like Service Error", e);
        }
    }

    public void delete(int id) {
        try {
            dao.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Like Service Error", e);
        }
    }
}
