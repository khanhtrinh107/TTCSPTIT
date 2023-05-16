package com.example.demo.service.impl;

import com.example.demo.entity.Comment;
import com.example.demo.entity.dto.CommentDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment findById(int id) throws UserNotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
    }

    @Override
    public List<Comment> findByBook(int BookId) {
        return commentRepository.findByBook(BookId);
    }

    @Override
    public List<Comment> findByUser(int userId) {
        return commentRepository.findByUser(userId);
    }

    @Override
    public Comment update(CommentDto commentDto, int id) throws UserNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new UserNotFoundException("No user have id " + commentDto.getUserId())));
        comment.setBook(bookRepository.findById(commentDto.getBookId()).orElseThrow(() -> new UserNotFoundException("No Book have id " + commentDto.getBookId())));
        comment.setContent(commentDto.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public Comment addComment(CommentDto commentDto) throws UserNotFoundException {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new UserNotFoundException("No user have id " + commentDto.getUserId())));
        comment.setBook(bookRepository.findById(commentDto.getBookId()).orElseThrow(() -> new UserNotFoundException("No Book have id " + commentDto.getBookId())));
        return commentRepository.save(comment);
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
        commentRepository.deleteById(id);
    }
}
