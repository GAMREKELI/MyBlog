package ru.gamrekeli.blogservice.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class BlogDto {
    String title;
    String content;
    Long authorId;
}
