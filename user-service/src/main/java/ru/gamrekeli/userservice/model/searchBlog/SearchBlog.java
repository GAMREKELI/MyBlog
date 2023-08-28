package ru.gamrekeli.userservice.model.searchBlog;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBlog {
    private Long authorId;
    private String search;
}
