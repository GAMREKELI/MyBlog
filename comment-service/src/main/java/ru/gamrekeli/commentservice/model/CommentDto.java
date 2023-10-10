package ru.gamrekeli.commentservice.model;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Value;

@Data
@Value
public class CommentDto {
    String text;
    Long authorId;
    String author;
    Long blogId;

    public String getText() {
        return text;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthor() {
        return author;
    }

    public Long getBlogId() {
        return blogId;
    }
}
