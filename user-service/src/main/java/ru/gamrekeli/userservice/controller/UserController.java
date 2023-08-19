package ru.gamrekeli.userservice.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.client.CommentClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.model.comment.Comment;
import ru.gamrekeli.userservice.securityConfig.authenticateComponent.SecurityComponent;
import ru.gamrekeli.userservice.service.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    // Ограничевание функционала пользователей между собой (авторизованный пользователь может редактировать только свою страницу)
    private final SecurityComponent securityComponent;

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

    // Отображение записей на странице пользователя

    @GetMapping("/with-blogs/{userId}")
    public String showAllBlogs(@PathVariable("userId") Long userId,
                               Model model, Authentication authentication) {
        List<Blog> blogs = blogClient.findAllBlogsByAuthorId(userId);
        boolean itsMe = securityComponent.checkUserByUserId(authentication, userId);

        model.addAttribute("itsMe", itsMe);
        model.addAttribute("blogs", blogs);
        model.addAttribute("userId", userId);
        return "showBlog/showAll";
    }
}
