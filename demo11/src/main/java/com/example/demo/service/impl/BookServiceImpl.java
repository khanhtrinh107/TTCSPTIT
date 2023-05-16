package com.example.demo.service.impl;

import com.example.demo.cloudinary.CloudinaryService;
import com.example.demo.entity.Book;
import com.example.demo.entity.dto.BookDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;


    @Override
    public Page<Book> findAll(int size, int page, String domain , String dir) {
        Sort sort = Sort.by(domain);
        if(dir.equals("asc"))
            sort.ascending();
        else if(dir.equals("desc"))
            sort.descending();
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return bookRepository.findAll(pageable);
    }


    @Override
    public Page<Book> searchByBookName(int size, int page, String domain, String dir, String keyword) {
        Sort sort = Sort.by(domain);
        if(dir.equals("asc"))
            sort.ascending();
        else if (dir.equals("desc"))
            sort.descending();
        Pageable pageable1 = PageRequest.of(page-1,size,sort);
        return bookRepository.searchByBookName(keyword,pageable1);
    }


    @Override
    public Book findById(int id) throws UserNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Book have id " + id));
    }

    @Override
    public Book create(BookDto BookDto) throws ObjectExistedException, IOException {
        if(!ObjectUtils.isEmpty(bookRepository.findByBookName(BookDto.getBookName()))){
            throw new ObjectExistedException("Book existed!");
        }
        Book Book = new Book();
        Book.setBookName(BookDto.getBookName());
        Book.setPrice(BookDto.getPrice());
        Book.setImage(cloudinaryService.uploadImage(BookDto.getImage()));
        Book.setDescription(BookDto.getDescription());
        Book.setCategory(categoryRepository.findByCategoryName(BookDto.getCategory()));
        Book.setAuthor(BookDto.getAuthor());
        Book.setPublicationDate(BookDto.getPublicationDate());
        System.out.println(BookDto.getAuthor());
        System.out.println(BookDto.getPublicationDate());
        return bookRepository.save(Book);
    }

    @Override
    public Book update(BookDto BookDto, int id) throws ObjectExistedException, UserNotFoundException, IOException {
        Book Book = bookRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Book have id " + id));
        System.out.println(BookDto.getBookName());
        if(!ObjectUtils.isEmpty(bookRepository.findByBookName(BookDto.getBookName()))){
            throw new ObjectExistedException("Book existed!");
        }
        if(BookDto.getBookName() != null)  Book.setBookName(BookDto.getBookName());
        if(BookDto.getPrice() != null) Book.setPrice(BookDto.getPrice());
        if(!ObjectUtils.isEmpty(BookDto.getImage())){
            Book.setImage(cloudinaryService.uploadImage(BookDto.getImage()));
        }
        if(BookDto.getDescription() != null) Book.setDescription(BookDto.getDescription());
        if(BookDto.getCategory() != null) Book.setCategory(categoryRepository.findByCategoryName(BookDto.getCategory()));
        if(BookDto.getAuthor() != null) Book.setAuthor(BookDto.getAuthor());
        if(BookDto.getPublicationDate() != null ) Book.setPublicationDate(BookDto.getPublicationDate());
        return bookRepository.save(Book);
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        Book Book = bookRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Produt have id " + id));
        bookRepository.deleteById(id);
    }

    @Override
    public List<Object[]> statistics() {
        return bookRepository.statitics();
    }

    @Override
    public Page<Book> getSameCategory(int BookId , int page , int size ) {
        Pageable pageable = PageRequest.of(page-1,size);
        return bookRepository.getSameCategory(BookId , pageable);
    }

    @Override
    public List<Book> getLastestBook(Pageable pageable) {
        return bookRepository.getLastestBook(pageable);
    }

    @Override
    public Page<Book> getBookByCategoryId(int id , int page , int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return bookRepository.getBookByCategoryId(id , pageable);
    }

    @Override
    public List<Book> getBookSale() {
        Pageable pageable = PageRequest.of(0,4);
        return bookRepository.getBookSale(pageable);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
