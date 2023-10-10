package ru.gamrekeli.commentservice.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gamrekeli.commentservice.model.CommentDto;
import ru.gamrekeli.commentservice.service.CommentService;

@Component
public class Consumer {

    private static final String orderTopic = "${spring.topic.name-comment-add}";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentService commentService;

    @KafkaListener(topics = orderTopic)
    public void consumeComment(String message) throws JsonProcessingException {
        CommentDto commentDto = objectMapper.readValue(message, CommentDto.class);
        commentService.persistComment(commentDto);
    }
}
