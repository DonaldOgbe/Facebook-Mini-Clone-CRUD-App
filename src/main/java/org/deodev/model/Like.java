package org.deodev.model;

import org.deodev.dto.request.CreateLikeDTO;

import java.time.LocalDateTime;

public class Like {
    private int id;
    private int postId;
    private int commentId;
    private int userId;
    private LocalDateTime createdAt;

    public Like(CreateLikeDTO dto) {
        this.postId = dto.getPostId();
        this.commentId = dto.getCommentId();
        this.userId = dto.getUserId();
    }

    public Like() {};

//    Getters

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

//    Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
