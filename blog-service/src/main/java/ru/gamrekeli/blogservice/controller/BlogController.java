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

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("blogs", service.findAll());
        return "showBlog/showAll";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("blog") Blog blog) {
        return "addBlog/addBlog";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("blog") Blog blog) {
        service.add(blog);
        return "redirect:/blog";
    }
}
