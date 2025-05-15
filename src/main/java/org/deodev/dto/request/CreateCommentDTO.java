package org.deodev.dto.request;


public class CreateCommentDTO {

    private String content;
    private int userId;
    private int postId;

    public CreateCommentDTO(String content, int userId, int postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }

    public CreateCommentDTO() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }
}
