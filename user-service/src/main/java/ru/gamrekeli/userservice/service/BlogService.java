package ru.gamrekeli.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.userservice.consumer.BlogConsumer;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.producer.BlogProducer;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogProducer producer;

    @Autowired
    private BlogConsumer blogConsumer;

    public String createBlog(Blog blog) throws JsonProcessingException {
        return producer.sendMessageForAddBlog(blog);
    }

}
