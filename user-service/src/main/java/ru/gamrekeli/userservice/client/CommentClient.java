package ru.gamrekeli.userservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.gamrekeli.userservice.model.comment.Comment;

import java.util.List;

@HttpExchange
public interface CommentClient {
    @GetExchange("/comment/{blogId}")
    List<Comment> findAllCommentsByBlogId(@PathVariable("blogId") Long blogId);

    @PostExchange("/comment/add")
    public void save(@RequestBody Comment comment);

    @DeleteExchange("/comment/delete/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId);
}
