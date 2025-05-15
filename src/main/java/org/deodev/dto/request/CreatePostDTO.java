package org.deodev.dto.request;



public class CreatePostDTO {
    private String content;
    private int userId;

    public CreatePostDTO(String content, int userId) {
        this.content = content;
        this.userId = userId;
    }

    public CreatePostDTO() {}

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


