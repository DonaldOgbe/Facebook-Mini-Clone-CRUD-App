package org.deodev.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deodev.dto.request.CreatePostDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Post {
    private int id;
    private String content;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(CreatePostDTO dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        this.userId = dto.getUserId();
    }

    public Post() {
    }
}
