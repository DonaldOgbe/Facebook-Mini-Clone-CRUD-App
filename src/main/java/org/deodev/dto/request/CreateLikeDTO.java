package org.deodev.dto.request;

public class CreateLikeDTO {

    private int postId;
    private int commentId;
    private int userId;

    public CreateLikeDTO(int postId, int commentId, int userId) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
    }

    public CreateLikeDTO() {};

//    Getters

    public int getPostId() {
        return postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

//    Setters

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
