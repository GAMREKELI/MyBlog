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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.service.annotation.GetExchange;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.model.searchBlog.SearchBlog;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;
import ru.gamrekeli.userservice.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Slf4j
//@RequiredArgsConstructor
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
                               Model model,
                               Authentication authentication) {

        SearchBlog searchBlog = new SearchBlog();

        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
        List<Blog> blogs = blogClient.findAllBlogsByAuthorId("Bearer " + token, userId);
        boolean itsMe = securityComponent.checkUserByUserId(authentication, userId);

        model.addAttribute("search", searchBlog);
        model.addAttribute("itsMe", itsMe);
        model.addAttribute("blogs", blogs);
        model.addAttribute("userId", userId);
        model.addAttribute("searchBlog", searchBlog);

        return "showBlog/showAll";
    }

    @GetMapping("/blog/with-blogs/{userId}/search")
    public String searchBlog(@ModelAttribute("searchBlog") SearchBlog searchBlog,
                             @PathVariable("userId") Long userId,
                             Authentication authentication,
                             Model model) {
        if (searchBlog.getSearch().length() == 0 || searchBlog.getSearch() == null) {
            return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
        }
        else {
            if (securityComponent.checkUserByUserId(authentication, userId)) {
                String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
                searchBlog.setAuthorId(userId);

                List<Blog> blogSearch = blogClient.findAllBlogsByAuthorIdSearch("Bearer " + token, searchBlog);
                LOGGER.debug("***************   LLLLLOOOOOOLLLLLLL   ***************");
                model.addAttribute("id", userId);
                model.addAttribute("blogSearch", blogSearch);
                return "showBlog/showAllByTitle";
            }
        }
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }
}
