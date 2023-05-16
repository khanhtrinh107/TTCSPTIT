package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
   @Query("select p from Book p where p.BookName = ?1")
    Book findByBookName(String bookname);
    @Query("select p from Book p where p.BookName like %?1% ")
    Page <Book> searchByBookName(String BookName , Pageable pageable);

    @Query("select p.BookName , sum(o.quantity) , sum(o.initPrice*o.quantity) from Book p inner join OrderDetail o on p.BookId = o.Book.BookId group by p.BookId order by sum(o.initPrice*o.quantity) desc ")
    List<Object[]> statitics();

    @Query("select p from Book p where p.category.categoryId = ?1")
    Page<Book> getSameCategory(int BookId , Pageable pageable);

    @Query("select p from Book p order by p.BookId  desc  ")
    List<Book> getLastestBook(Pageable pageable);

    @Query("select p from Book p where p.category.categoryId = ?1")
    Page<Book> getBookByCategoryId(int id , Pageable pageable);

    @Query("select p from Book p order by p.BookId")
    List<Book> getBookSale(Pageable pageable);
}
