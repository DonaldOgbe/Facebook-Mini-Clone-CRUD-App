package org.deodev.model;

import lombok.Data;
import org.deodev.dto.request.CreatePostDTO;

import java.time.LocalDateTime;

@Data
public class Post {
    private int id;
    private String content;
    private int userId;
    private LocalDateTime createdAt;

    public Post(CreatePostDTO dto) {
        this.content = dto.getContent();
        this.userId = dto.getUserId();
    }
}
