package ru.gamrekeli.blogservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gamrekeli.blogservice.service.BlogService;

@Component
public class Producer {

    @Value("${spring.topic.name-blog-getAll}")
    private String getBlogs;
    @Value("${spring.topic.name-blog-pushAll}")
    private String pushBlogs;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageWithBlogs(String message) throws JsonProcessingException {
        kafkaTemplate.send(pushBlogs, message);
    }

}
