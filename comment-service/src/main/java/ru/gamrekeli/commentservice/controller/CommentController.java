package ru.gamrekeli.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.commentservice.model.Comment;
import ru.gamrekeli.commentservice.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    @GetMapping("/{blogId}")
    public ResponseEntity<List<Comment>> findAllCommentsByBlogId(@PathVariable("blogId") Long blogId) {
        return ResponseEntity.ok(commentService.findAllByBlogId(blogId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Comment comment) {
        commentService.add(comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> delete(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
