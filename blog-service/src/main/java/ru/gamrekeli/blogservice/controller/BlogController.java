package ru.gamrekeli.blogservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.model.searchBlog.SearchBlog;
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

    @GetMapping("/user/search")
    public ResponseEntity<List<Blog>> findAllBlogsByAuthorIdSearch(@RequestHeader("Authorization") String authorizationHeader,
                                                                   @RequestBody SearchBlog searchBlog) {
        return ResponseEntity.ok(service.findAllByAuthorIdSearch(searchBlog.getAuthorId(), searchBlog.getSearch()));
    }

    @PostMapping("/add")
    public void save(@RequestHeader("Authorization") String authorizationHeader,
                                  @RequestBody Blog blog) {
        LOGGER.debug(blog.toString());
        service.add(blog);
    }

    @DeleteMapping("/delete/{blogId}")
    public void delete(@RequestHeader("Authorization") String authorizationHeader,
                                    @PathVariable("blogId") Long blogId) {
        service.deleteByBlogId(blogId);
    }
}
