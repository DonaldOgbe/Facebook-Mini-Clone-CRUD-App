package org.deodev.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {

    private String content;
    private int userId;
    private int postId;
}
