package ru.gamrekeli.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.model.searchBlog.SearchBlog;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;


@Controller
@RequestMapping("/api/v1/blog")
@Slf4j
public class BlogController {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(BlogController.class);

    // Ограничевание функционала пользователей между собой (авторизованный пользователь может редактировать только свою страницу)
    @Autowired
    private SecurityComponent securityComponent;

    @Autowired
    private BlogClient blogClient;

    @GetMapping("/{userId}/add-blog")
    public String showCreateBlogForm(@PathVariable("userId") Long userId,
                                     Model model,
                                     Authentication authentication,
                                     @ModelAttribute("blog") Blog blog) {
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            model.addAttribute("userId", userId);
            return "addBlog/addBlog";
        }
        else {
            return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
        }
    }

    @GetMapping("/{userId}/create")
    public String addBlog(@ModelAttribute("blog") Blog blog,
                          @PathVariable("userId") Long userId,
                          Authentication authentication) {
        LOGGER.debug("************* START PostMapping /{userId}/create *************");
//        LOGGER.debug(blog.toString());

        if (securityComponent.checkUserByUserId(authentication, userId)) {
            String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
            blog.setAuthorId(userId);
            blogClient.save("Bearer " + token, blog);
        }
        LOGGER.debug("************* STOP PostMapping /{userId}/create *************");
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }

    @GetMapping("/{userId}/{blogId}")
    public String deleteBlogById(@PathVariable("blogId") Long blogId,
                                 @PathVariable("userId") Long userId,
                                 Authentication authentication) {
        LOGGER.debug("Entering showAllBlogs method");
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            String token = ((Jwt)authentication.getPrincipal()).getTokenValue();

            log.info(String.valueOf("*************************************** TEST STRING ***************************************" ));

            blogClient.delete("Bearer " + token, blogId);
        }
        LOGGER.debug("Exiting showAllBlogs method");
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }

    @GetMapping("/search")
    public String searchBlog(@ModelAttribute("searchBlog")SearchBlog searchBlog) {

        return "showAllByTitle";
    }
}
