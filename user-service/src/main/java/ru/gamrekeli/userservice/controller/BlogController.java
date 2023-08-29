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
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;

import java.util.List;


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
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
            blog.setAuthorId(userId);
            blogClient.save("Bearer " + token, blog);
        }
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }

    @GetMapping("/{userId}/{blogId}")
    public String deleteBlogById(@PathVariable("blogId") Long blogId,
                                 @PathVariable("userId") Long userId,
                                 Authentication authentication) {
        LOGGER.debug("Entering showAllBlogs method");
        if (securityComponent.checkUserByUserId(authentication, userId)) {
            String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
            blogClient.delete("Bearer " + token, blogId);
        }
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }

    @GetMapping("/with-blogs/{userId}/search")
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
                model.addAttribute("id", userId);
                model.addAttribute("blogSearch", blogSearch);
                return "showBlog/showAllByTitle";
            }
        }
        return "redirect:http://127.0.0.1:9494/api/v1/with-blogs/" + userId;
    }
}
