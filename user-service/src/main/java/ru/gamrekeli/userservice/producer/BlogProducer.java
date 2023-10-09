package ru.gamrekeli.userservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.blog.Blog;

import java.util.List;

@Component
public class BlogProducer {

    @Value("${spring.topic.name-blog-add}")
    private String addBlogTopic;

    @Value("${spring.topic.name-blog-getAll}")
    private String getBlogs;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageForAddBlog(Blog blog) throws JsonProcessingException {
        String blogAsMessage = objectMapper.writeValueAsString(blog); // Преобразование из объекта в строку
        kafkaTemplate.send(addBlogTopic, blogAsMessage);

        return "message send";
    }

    public String sendMessageForGetBlogs(Long authorId) {
        kafkaTemplate.send(getBlogs, authorId.toString());

        return "message send";
    }

}
