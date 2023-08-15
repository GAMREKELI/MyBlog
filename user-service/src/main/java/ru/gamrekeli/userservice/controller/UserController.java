package ru.gamrekeli.userservice.controller;

import jakarta.ws.rs.PathParam;
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

    @GetMapping("/with-blogs/{authorId}")
    public String showAllBlogs(@PathVariable("authorId") Long authorId, Model model) {
        List<Blog> blogs = blogClient.findAllBlogsByAuthorId(authorId);

        model.addAttribute("blogs", blogs);
        return "showBlog/showAll";
    }


    // Создание блога:

    @GetMapping("/{userId}/add-blog")
    public String showCreateBlogForm(@PathVariable("userId") Long userId, Model model) {
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        model.addAttribute("blog", blog);
        return "addBlog/addBlog";
    }

    @PostMapping("/create")
    public String addBlog(@ModelAttribute("blog") Blog blog) {
//        try {
            blogClient.save(blog);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Дополнительная обработка исключения, например, логирование
//        }
        return "redirect:/api/v1";
    }

}
