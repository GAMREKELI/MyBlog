package ru.gamrekeli.blogservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gamrekeli.blogservice.model.BlogDto;
import ru.gamrekeli.blogservice.service.BlogService;

@Component
public class Consumer {

    private static final String orderTopic = "${spring.topic.name}";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogService blogService;

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {

        BlogDto blogDto = objectMapper.readValue(message, BlogDto.class);
        blogService.persistBlog(blogDto);
    }
}
