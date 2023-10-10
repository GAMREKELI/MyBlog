package ru.gamrekeli.userservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.blog.Blog;

@Component
public class BlogProducer {

    @Value("${spring.topic.name-blog-add}")
    private String addBlogTopic;

    @Value("name-blog-delete")
    private String deleteBlogTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageForAddBlog(Blog blog) throws JsonProcessingException {
        String blogAsMessage = objectMapper.writeValueAsString(blog); // Преобразование из объекта в строку
        System.out.println(blogAsMessage);//+
        kafkaTemplate.send(addBlogTopic, blogAsMessage);

        return "message for add blog send";
    }

    public String sendMessageForDeleteBlog(Long blogId) throws JsonProcessingException {
        String blogAsMessage = objectMapper.writeValueAsString(blogId);
//        System.out.println(commentMessage);
        kafkaTemplate.send(deleteBlogTopic, blogAsMessage);
        return "message for delete blog send";
    }
}
