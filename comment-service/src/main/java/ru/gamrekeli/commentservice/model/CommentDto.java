package ru.gamrekeli.commentservice.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class CommentDto {
    String text;
    Long authorId;
    String author;
    Long blogId;
}
