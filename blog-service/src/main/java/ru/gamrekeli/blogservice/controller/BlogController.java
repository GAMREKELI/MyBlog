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

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(BlogController.class);

    private final BlogService service;

    @GetMapping("/user/{authorId}")
    public ResponseEntity<List<Blog>> findAllBlogsByAuthorId(@RequestHeader("Authorization") String authorizationHeader,
                                                             @PathVariable("authorId") Long authorId) {
        return ResponseEntity.ok(service.findAllByAuthorId(authorId));
    }

    @PostMapping("/add")
    public void save(@RequestHeader("Authorization") String authorizationHeader,
                                  @RequestBody Blog blog) {
        LOGGER.debug("************* START PostMapping BLOG *************");
        LOGGER.debug(blog.toString());
        service.add(blog);
    }

    @DeleteMapping("/delete/{blogId}")
    public void delete(@RequestHeader("Authorization") String authorizationHeader,
                                    @PathVariable("blogId") Long blogId) {
        service.deleteByBlogId(blogId);
    }
}
