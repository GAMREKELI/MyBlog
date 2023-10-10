package ru.gamrekeli.blogservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gamrekeli.blogservice.model.BlogDto;
import ru.gamrekeli.blogservice.service.BlogService;

import java.util.List;

//@Slf4j
@Component
public class Consumer {

    private static final String orderTopicForAddBlog = "${spring.topic.name-blog-add}";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogService blogService;

    @KafkaListener(topics = orderTopicForAddBlog)
    public void consumeMessageForAddBlog(String message) throws JsonProcessingException {
        BlogDto blogDto = objectMapper.readValue(message, BlogDto.class);
        blogService.addBlog(blogDto);
    }

    @KafkaListener(topics = "name-blog-delete")
    public void consumerMessageForDeleteBlog(String message) throws JsonProcessingException {
        Long blogId = objectMapper.readValue(message, Long.class);
        blogService.deleteByBlogId(blogId);
    }
}
