package ru.gamrekeli.blogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.service.BlogService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final BlogService service;

    @GetMapping("/user/{authorId}")
    public ResponseEntity<List<Blog>> findAllBlogsByAuthorId(@PathVariable("authorId") Long authorId) {
        return ResponseEntity.ok(service.findAllByAuthorId(authorId));
    }

//    @GetMapping("/add")
//    public String findAll(@RequestBody Blog blog, Model model) {
//        model.addAttribute("blog");
//        return "addBlog/addBlog";
//    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Blog blog) {
        service.add(blog);
        return ResponseEntity.ok().build();
    }
}
