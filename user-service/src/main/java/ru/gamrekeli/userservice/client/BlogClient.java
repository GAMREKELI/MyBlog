package ru.gamrekeli.userservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.gamrekeli.userservice.model.blog.Blog;

import java.util.List;

@HttpExchange
public interface BlogClient {
    @GetExchange("/blog/user/{authorId}")
    List<Blog> findAllBlogsByAuthorId(@PathVariable("authorId") Long authorId);

    @PostExchange("/blog/add")
    public void save(@RequestBody Blog blog);

    @DeleteExchange("/blog/delete/{blogId}")
    public void delete(@PathVariable("blogId") Long blogId);
}
