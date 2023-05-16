package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("select c from Comment c where c.Book.BookId = ?1")
    List<Comment> findByBook(int BookId);

    @Query("select c from Comment c where c.user.userId = ?1")
    List<Comment> findByUser(int userId);
}
