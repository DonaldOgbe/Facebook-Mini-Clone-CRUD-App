package org.deodev.dto.response.post;

import org.deodev.model.Comment;
import org.deodev.model.Post;


public class GetPostResponseDTO {

    private Post post;
    private Comment comment;
    private int numberOfComments;

    public GetPostResponseDTO(Post post, Comment comment, int numberOfComments) {
        this.post = post;
        this.comment = comment;
        this.numberOfComments = numberOfComments;
    }

    public GetPostResponseDTO() {}

//    Getters


    public Post getPost() {
        return post;
    }

    public Comment getComment() {
        return comment;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }


//    Setters


    public void setPost(Post post) {
        this.post = post;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
}
