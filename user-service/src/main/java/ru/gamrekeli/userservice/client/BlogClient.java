package ru.gamrekeli.userservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.gamrekeli.userservice.model.blog.Blog;

import java.util.List;

@HttpExchange
public interface BlogClient {
    @GetExchange("/blog/user/{authorId}")
    List<Blog> findAllBlogsByAuthorId(@RequestHeader("Authorization") String authorizationHeader,
                                      @PathVariable("authorId") Long authorId);

    @GetExchange("/blog/user/{authorId}/search")
    List<Blog> findAllBlogsByAuthorIdSearch(@RequestHeader("Authorization") String authorizationHeader,
                                      @PathVariable("authorId") Long authorId, String title);

    @PostExchange("/blog/add")
    public void save(@RequestHeader("Authorization") String authorizationHeader,
                     @RequestBody Blog blog);

    @DeleteExchange("/blog/delete/{blogId}")
    public void delete(@RequestHeader("Authorization") String authorizationHeader,
                       @PathVariable("blogId") Long blogId);
}
