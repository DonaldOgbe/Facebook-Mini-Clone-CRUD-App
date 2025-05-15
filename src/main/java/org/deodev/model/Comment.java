package org.deodev.model;


import org.deodev.dto.request.CreateCommentDTO;
import java.time.LocalDateTime;

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

    public void setId(int id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
