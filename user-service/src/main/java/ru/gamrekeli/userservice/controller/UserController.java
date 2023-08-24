package ru.gamrekeli.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;
import ru.gamrekeli.userservice.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Ограничевание функционала пользователей между собой (авторизованный пользователь может редактировать только свою страницу)
    @Autowired
    private SecurityComponent securityComponent;

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
                               Model model, Authentication authentication,
                               HttpServletRequest request) {
//        LOGGER.debug("Entering showAllBlogs method");
        LOGGER.debug("Entering showAllBlogs method");
        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
        List<Blog> blogs = blogClient.findAllBlogsByAuthorId("Bearer " + token, userId);
        boolean itsMe = securityComponent.checkUserByUserId(authentication, userId);

//        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String csrfToken = request.getHeader("X-CSRF-TOKEN");
//        System.out.println("CSRF Token: " + csrfToken);
////        LOGGER.info("CSRF Token: {}", csrfToken);
//
//
//        System.out.println(itsMe);
//        log.info(String.valueOf(itsMe));
        model.addAttribute("itsMe", itsMe);
        model.addAttribute("blogs", blogs);
        model.addAttribute("userId", userId);


        LOGGER.debug("Exiting showAllBlogs method");


        return "showBlog/showAll";
    }
}
