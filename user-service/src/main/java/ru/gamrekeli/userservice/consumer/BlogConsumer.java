package ru.gamrekeli.userservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.blog.Blog;
import ru.gamrekeli.userservice.service.BlogService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class BlogConsumer {

//    private static final String orderTopic = "${spring.topic}";
    private static final String orderTopic = "${spring.topic.name-blog-pushAll}";

    private CompletableFuture<List<Blog>> futureBlogs;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogService blogService;

    @KafkaListener(topics = orderTopic)
    public void getAllBlogs(String message) throws JsonProcessingException {
        List<Blog> blogs = objectMapper.readValue(message, new TypeReference<List<Blog>>() {});
        futureBlogs.complete(blogs);
    }
}
