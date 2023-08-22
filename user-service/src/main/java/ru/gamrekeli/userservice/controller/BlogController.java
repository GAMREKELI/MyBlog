package ru.gamrekeli.userservice.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;


@Controller
//@AllArgsConstructor
@RequestMapping("/api/v1/blog")
@Slf4j
public class BlogController {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(BlogController.class);

    private final Logger logger = LoggerFactory.getLogger(BlogController.class);

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
            return "redirect:/api/v1/with-blogs/" + userId;
        }
    }

    @PostMapping("/{userId}/create")
    public String addBlog(@ModelAttribute("blog") Blog blog,
                          @PathVariable("userId") Long userId,
                          Authentication authentication) {
        logger.info("Request received to fetch blogs for authorId: {}", userId);
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            blog.setAuthorId(userId);
            blogClient.save(blog);
        }
        return "redirect:/api/v1/with-blogs/" + userId;
    }

    @DeleteMapping("/{userId}/{blogId}")
    public String deleteBlogById(@PathVariable("blogId") Long blogId,
                                 @PathVariable("userId") Long userId, Authentication authentication) {
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            blogClient.delete(blogId);
        }
        return "redirect:/api/v1/with-blogs/" + userId;
    }
}
