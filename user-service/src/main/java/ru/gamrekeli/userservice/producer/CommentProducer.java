package ru.gamrekeli.userservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.comment.Comment;

@Component
public class CommentProducer {
    @Value("${spring.topic.name-comment-add}")
    private String commentTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageForAddComment(Comment comment) throws JsonProcessingException {
        String commentMessage = objectMapper.writeValueAsString(comment);
        System.out.println(commentMessage);//
        kafkaTemplate.send(commentTopic, commentMessage);

        return "message send";
    }
}
