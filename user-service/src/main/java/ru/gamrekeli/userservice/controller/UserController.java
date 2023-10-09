package ru.gamrekeli.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.model.searchBlog.SearchBlog;
import ru.gamrekeli.userservice.producer.BlogProducer;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;
import ru.gamrekeli.userservice.service.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Ограничевание функционала пользователей между собой
    // (авторизованный пользователь может редактировать только свою страницу)
    @Autowired
    private SecurityComponent securityComponent;

    @Autowired
    private BlogClient blogClient;
    @Autowired
    private BlogProducer blogProducer;


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
                               Model model,
                               Authentication authentication) {

        SearchBlog searchBlog = new SearchBlog();

        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();

//        List<Blog> blogs = blogClient.findAllBlogsByAuthorId("Bearer " + token, userId);
        blogProducer.sendMessageForGetBlogs(userId);
        CompletableFuture<List<Blog>> futureBlogs = new CompletableFuture<>();
        boolean itsMe = securityComponent.checkUserByUserId(authentication, userId);

        List<Blog> blogs = futureBlogs.join();
        model.addAttribute("search", searchBlog);
        model.addAttribute("itsMe", itsMe);
        model.addAttribute("blogs", blogs);
        model.addAttribute("userId", userId);
        model.addAttribute("searchBlog", searchBlog);

        return "showBlog/showAll";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:http://127.0.0.1:9001/logout";
    }
}
