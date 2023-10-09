package ru.gamrekeli.blogservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.model.BlogDto;
import ru.gamrekeli.blogservice.producer.Producer;
import ru.gamrekeli.blogservice.service.BlogService;

import java.util.List;

@Component
public class Consumer {

    private static final String orderTopicForAddBlog = "${spring.topic.name-blog-add}";
    private static final String orderTopicForGetBlogs = "${spring.topic.name-blog-getAll}";
    @Autowired
    private Producer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogService blogService;

    @KafkaListener(topics = orderTopicForAddBlog)
    public void consumeMessageForAddBlog(String message) throws JsonProcessingException {

        BlogDto blogDto = objectMapper.readValue(message, BlogDto.class);
        blogService.persistBlog(blogDto);
    }

    @KafkaListener(topics = orderTopicForGetBlogs)
    public void consumeMessageForGetBlogs(String message) throws JsonProcessingException {
        Long authorId = Long.parseLong(message);
        List<Blog> blogs = blogService.findAllByAuthorId(authorId);

        String blogsJson = objectMapper.writeValueAsString(blogs);
        producer.sendMessageWithBlogs(blogsJson);

    }
}
