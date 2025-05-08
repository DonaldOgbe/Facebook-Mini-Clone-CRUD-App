package org.deodev.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {
    private int id;
    private String content;
    private int userId;
}
