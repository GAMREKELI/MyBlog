package ru.gamrekeli.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.client.CommentClient;
import ru.gamrekeli.userservice.model.comment.Comment;
import ru.gamrekeli.userservice.securityConfig.authenticateComponent.SecurityComponent;
import ru.gamrekeli.userservice.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentClient commentClient;

    @GetMapping("/with-blogs/{userId}/{blogId}")
    public String showAllComment(@PathVariable("userId") Long userId,
                                 @PathVariable("blogId") Long blogId,
                                 Model model,
                                 @ModelAttribute("commentForBlog") Comment comment) {
        model.addAttribute("comments", commentClient.findAllCommentsByBlogId(blogId));
        model.addAttribute("blogId", blogId);
        model.addAttribute("userId", userId);
        return "showComment/showAll";

    }

    @PostMapping("/with-blogs/{userId}/{blogId}/create")
    public String addComment(@ModelAttribute("commentForBlog") Comment comment,
                             @PathVariable("userId") Long userId, @PathVariable("blogId") Long blogId,
                             Authentication authentication) {
        comment.setAuthorId(userId);
        comment.setBlogId(blogId);
        comment.setAuthor(authentication.getName());
        commentClient.save(comment);
        return "redirect:/api/v1/comment/with-blogs/" + userId + "/" + blogId;
    }

    @DeleteMapping("/with-blogs/{userId}/{blogId}/{commentId}")
    public String deleteCommentById(@PathVariable("blogId") Long blogId,
                                    @PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {
        commentClient.delete(commentId);
        return "redirect:/api/v1/comment/with-blogs/" + userId + "/" + blogId;
    }
}
