package ru.gamrekeli.commentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.commentservice.model.Comment;
import ru.gamrekeli.commentservice.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    public List<Comment> findAllByBlogId(Long blogId) {
        return repository.findAllByBlogId(blogId);
    }

    public void add(Comment comment) {
        repository.save(comment);
    }

    public void deleteComment(Long commentId) {
        repository.deleteById(commentId);
    }
}
