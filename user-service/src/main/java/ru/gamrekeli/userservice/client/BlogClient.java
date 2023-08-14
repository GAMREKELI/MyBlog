package ru.gamrekeli.userservice.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.gamrekeli.userservice.model.blog.Blog;

import java.util.List;

@HttpExchange
public interface BlogClient {
    @GetExchange("/blog/user/{authorId}")
    List<Blog> findAllBlogsByAuthorId(@PathVariable("authorId") Long authorId);
}
