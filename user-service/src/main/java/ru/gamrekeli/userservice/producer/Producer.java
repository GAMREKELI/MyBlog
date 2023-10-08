package ru.gamrekeli.userservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.blog.Blog;

@Component
public class Producer {

    @Value("${spring.topic.name}")
    private String blogTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageForAddBlog(Blog blog) throws JsonProcessingException {
        String blogAsMessage = objectMapper.writeValueAsString(blog);
        kafkaTemplate.send(blogTopic, blogAsMessage);

        return "message send";
    }
}
