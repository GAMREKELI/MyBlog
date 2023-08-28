package ru.gamrekeli.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gamrekeli.blogservice.model.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByAuthorId(Long authorId);


    @Query("SELECT b FROM Blog b WHERE b.authorId = :authorId AND b.title LIKE %:title%")
    List<Blog> findByTitleContaining(@Param("authorId") Long authorId, @Param("title") String title);
}
