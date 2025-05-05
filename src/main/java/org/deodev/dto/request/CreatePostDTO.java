package org.deodev.dto.request;

import lombok.Data;

@Data
public class CreatePostDTO {
    private String content;
    private int userId;

    public CreatePostDTO(String content, int userId) {
        this.content = content;
        this.userId = userId;
    }

    public CreatePostDTO() {};
}
