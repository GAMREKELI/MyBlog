package ru.gamrekeli.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.service.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    private BlogClient blogClient;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "showUsers/showAll";
    }

    @GetMapping("/{userId}")
    public String showUserById(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId).get());
        return "showUsers/showUser";
    }

    @GetMapping("/with-blogs/{userId}")
    public String showAllBlogs(@PathVariable("userId") Long userId, Model model) {
        List<Blog> blogs = blogClient.findAllBlogsByAuthorId(userId);

        model.addAttribute("blogs", blogs);
        model.addAttribute("userId", userId);
        return "showBlog/showAll";
    }


    // Создание блога:

    @GetMapping("/{userId}/add-blog")
    public String showCreateBlogForm(@PathVariable("userId") Long userId, Model model) {
        Blog blog = new Blog();
        model.addAttribute("userId", userId);
        model.addAttribute("blog", blog);
        return "addBlog/addBlog";
    }

    @PostMapping("/{userId}/create")
    public String addBlog(@ModelAttribute("blog") Blog blog, @PathVariable("userId") Long userId) {
        blog.setAuthorId(userId);
//        try {
            blogClient.save(blog);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Дополнительная обработка исключения, например, логирование
//        }
        return "redirect:/api/v1/with-blogs/" + userId;
    }

    @DeleteMapping("/{userId}/{blogId}")
    public String deleteBlogById(@PathVariable("blogId") Long blogId, @PathVariable("userId") Long userId) {
        blogClient.delete(blogId);
        return "redirect:/api/v1/with-blogs/" + userId;
    }

}
