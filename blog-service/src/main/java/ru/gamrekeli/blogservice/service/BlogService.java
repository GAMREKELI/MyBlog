package ru.gamrekeli.blogservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamrekeli.blogservice.model.Blog;
import ru.gamrekeli.blogservice.model.BlogDto;
import ru.gamrekeli.blogservice.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public void add(Blog blog) {
        repository.save(blog);
    }


    public List<Blog> findAllByAuthorId(Long authorId) {
        return repository.findAllByAuthorId(authorId);
    }

    public List<Blog> findAllByAuthorIdSearch(Long authorId, String title) {
        return repository.findByTitleContaining(authorId, title);
    }

    public void deleteByBlogId(Long blogId) {
        repository.deleteById(blogId);
    }

    public void persistBlog(BlogDto blogDto) {
        Blog blog = modelMapper.map(blogDto, Blog.class);
        Blog persistedBlog = repository.save(blog);
    }
}
