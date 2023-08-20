package ru.gamrekeli.authenticationservice.model.blog;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    private Long blogId;
    private String title;
    private String content;
    private Long authorId;
}