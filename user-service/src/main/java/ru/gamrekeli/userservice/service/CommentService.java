package ru.gamrekeli.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.userservice.model.comment.Comment;
import ru.gamrekeli.userservice.producer.CommentProducer;

@Service
public class CommentService {

    @Autowired
    private CommentProducer producer;

    public String createComment(Comment comment) throws JsonProcessingException {
        return producer.sendMessageForAddComment(comment);
    }

    public String deleteComment(Long commentId) throws JsonProcessingException {
        return producer.sendMessageForDeleteComment(commentId);
    }
}
