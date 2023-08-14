package ru.gamrekeli.userservice.model.blog;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    private String title;
    private String content;
    private Long authorId;
}