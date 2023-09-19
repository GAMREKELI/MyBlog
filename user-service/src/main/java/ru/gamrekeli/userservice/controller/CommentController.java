package ru.gamrekeli.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.CommentClient;
import ru.gamrekeli.userservice.model.comment.Comment;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private SecurityComponent securityComponent;

    @GetMapping("/with-blogs/{userId}/{blogId}")
    public String showAllComment(@PathVariable("userId") Long userId,
                                 @PathVariable("blogId") Long blogId,
                                 Model model,
                                 @ModelAttribute("commentForBlog") Comment comment,
                                 Authentication authentication) {
        boolean itsMe = securityComponent.checkUserByUserId(authentication, userId);

        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
        model.addAttribute("comments", commentClient.findAllCommentsByBlogId("Bearer " + token, blogId));
        model.addAttribute("blogId", blogId);
        model.addAttribute("userId", userId);
        model.addAttribute("itsMe", itsMe);
        return "showComment/showAll";

    }

    @GetMapping("/with-blogs/{userId}/{blogId}/create")
    public String addComment(@ModelAttribute("commentForBlog") Comment comment,
                             @PathVariable("userId") Long userId, @PathVariable("blogId") Long blogId,
                             Authentication authentication) {
        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
        comment.setAuthorId(userId);
        comment.setBlogId(blogId);
        comment.setAuthor(authentication.getName());
        commentClient.save("Bearer " + token, comment);
        return "redirect:http://127.0.0.1:9494/api/v1/comment/with-blogs/" + userId + "/" + blogId;
    }

    @GetMapping("/with-blogs/{userId}/{blogId}/{commentId}")
    public String deleteCommentById(@PathVariable("blogId") Long blogId,
                                    @PathVariable("userId") Long userId,
                                    @PathVariable("commentId") Long commentId,
                                    Authentication authentication) {
        String token = ((Jwt)authentication.getPrincipal()).getTokenValue();
        commentClient.delete("Bearer " + token, commentId);
        return "redirect:http://127.0.0.1:9494/api/v1/comment/with-blogs/" + userId + "/" + blogId;
    }
}
