package ru.gamrekeli.commentservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.commentservice.model.Comment;
import ru.gamrekeli.commentservice.model.CommentDto;
import ru.gamrekeli.commentservice.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Comment> findAllByBlogId(Long blogId) {
        return repository.findAllByBlogId(blogId);
    }

    public void add(Comment comment) {
        repository.save(comment);
    }

    public void deleteComment(Long commentId) {
        repository.deleteById(commentId);
    }

    public void persistComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Comment persistComment = repository.save(comment);
    }
}
