package org.deodev.model;

import lombok.*;
import org.deodev.dto.request.CreateCommentDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Comment {

    private int id;
    private String content;
    private int userId;
    private int postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment(CreateCommentDTO dto) {
        this.content = dto.getContent();
        this.userId = dto.getUserId();
        this.postId = dto.getPostId();
    }


}
