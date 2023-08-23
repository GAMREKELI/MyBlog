package ru.gamrekeli.blogservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.service.BlogService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(BlogController.class);

    private final BlogService service;

    @GetMapping("/user/{authorId}")
    public ResponseEntity<List<Blog>> findAllBlogsByAuthorId(@PathVariable("authorId") Long authorId) {
        return ResponseEntity.ok(service.findAllByAuthorId(authorId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Blog blog) {
        service.add(blog);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<?> delete(@PathVariable("blogId") Long blogId) {
        service.deleteByBlogId(blogId);
        return ResponseEntity.ok().build();
    }
}
