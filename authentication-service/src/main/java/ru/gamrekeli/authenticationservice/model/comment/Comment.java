package ru.gamrekeli.authenticationservice.model.comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Long commentId;

    private String text;
    private Long authorId;
    private String author;
    private Long blogId;
}
