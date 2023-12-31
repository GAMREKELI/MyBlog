package ru.gamrekeli.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.producer.BlogProducer;

@Service
public class BlogService {

    @Autowired
    private BlogProducer producer;

    public String createBlog(Blog blog) throws JsonProcessingException {
        return producer.sendMessageForAddBlog(blog);
    }

    public String sendMessageForDeleteBlog(Long blogId) throws JsonProcessingException {
        return producer.sendMessageForDeleteBlog(blogId);
    }

}
