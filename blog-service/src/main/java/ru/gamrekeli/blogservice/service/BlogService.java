package ru.gamrekeli.blogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repository;

    public void add(Blog blog) {
        repository.save(blog);
    }

    public List<Blog> findAll() {
        return repository.findAll();
    }

    public List<Blog> findAllByAuthorId(Long authorId) {
        return repository.findAllByAuthorId(authorId);
    }

    public void deleteByBlogId(Long blogId) {
        repository.deleteById(blogId);
    }
}
