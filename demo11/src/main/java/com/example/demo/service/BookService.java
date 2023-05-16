package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.dto.BookDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Page<Book> findAll(int size , int page , String domain , String dir);
    Page<Book> searchByBookName(int size , int page , String domain , String dir , String keyword);
    Book findById(int id) throws UserNotFoundException;
    Book create(BookDto BookDto) throws ObjectExistedException, IOException;
    Book update(BookDto BookDto , int id) throws ObjectExistedException, UserNotFoundException, IOException;
    void delete(int id) throws UserNotFoundException;

    List<Object[]> statistics();

    Page<Book> getSameCategory(int BookId , int page,  int size);

    List<Book> getLastestBook(Pageable pageable);

    Page<Book> getBookByCategoryId(int id , int page , int size);

    List<Book> getBookSale();

    List<Book> getAll();
}
