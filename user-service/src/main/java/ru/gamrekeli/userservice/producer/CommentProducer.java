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
    private String addCommentTopic;

    @Value("${spring.topic.name-comment-delete}")
    private String deleteCommentTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageForAddComment(Comment comment) throws JsonProcessingException {
        String commentMessage = objectMapper.writeValueAsString(comment);
        kafkaTemplate.send(addCommentTopic, commentMessage);

        return "message send";
    }

    public String sendMessageForDeleteComment(Long commentId) throws JsonProcessingException {
        String commentMessage = objectMapper.writeValueAsString(commentId);
        kafkaTemplate.send(deleteCommentTopic, commentMessage);

        return "message send";
    }
}
