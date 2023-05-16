package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.dto.CartMessage;
import com.example.demo.entity.dto.BookDto;
import com.example.demo.entity.dto.BookView;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService BookService;
        @GetMapping("/Book")
    public ResponseEntity<?> getBooks(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size") int size , @RequestParam(name = "domain") String domain , @RequestParam(name = "dir") String dir){
        List<Book> list = BookService.findAll(size,page,domain,dir).getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/Book/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) throws UserNotFoundException {
            int id2 = Integer.parseInt(id);
            return new ResponseEntity<>(BookService.findById(id2), HttpStatus.OK);
    }
    @GetMapping("/Book/search")
    public ResponseEntity<?> searchBook(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" ,required = false , defaultValue = "6") int size,@RequestParam(name = "domain" , required = false , defaultValue = "BookId") String domain , @RequestParam(name = "dir" ,required = false , defaultValue = "asc") String dir , @RequestParam(name = "keyword" ,required = false , defaultValue = "") String keyword){
        List<Book> list = BookService.searchByBookName(size,page,domain,dir,keyword).getContent();
        int pageCount = BookService.searchByBookName(size,page,domain,dir,keyword).getTotalPages();
        BookView BookView = new BookView();
        BookView.setBookList(list);
        BookView.setPageCount(pageCount);
        return new ResponseEntity<>(BookView,HttpStatus.OK);
    }
   // @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/Book")
    public ResponseEntity<?> addBook(@RequestParam(name = "BookName") String BookName  , @RequestParam(name = "image")MultipartFile file, @RequestParam(name = "price") String price , @RequestParam(name = "category") String category , @RequestParam(name = "description") String description , @RequestParam(name = "publicationDate")String publicationDate , @RequestParam(name = "author") String author) throws ObjectExistedException, IOException, ParseException {
            BookDto BookDto = new BookDto();
            BookDto.setBookName(BookName);
            BookDto.setCategory(category);
            BookDto.setDescription(description);
            BookDto.setImage(file);
            BookDto.setPrice(price);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        Date date = null;
        date = formatter.parse(publicationDate);
            BookDto.setPublicationDate(date);
            BookDto.setAuthor(author);
            return new ResponseEntity<>(BookService.create(BookDto) , HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PutMapping("/Book/{id}")
    public ResponseEntity<?> updateBook(@RequestParam(name = "BookName" , required = false) String BookName  , @RequestParam(name = "image" ,required = false)MultipartFile file, @RequestParam(name = "price" ,required = false) String price , @RequestParam(name = "category" , required = false) String category , @RequestParam(name = "description" , required = false) String description , @PathVariable int id) throws UserNotFoundException, ObjectExistedException, IOException {
        BookDto BookDto = new BookDto();
        if(price != null) BookDto.setPrice(price);
        if(BookName != null)  BookDto.setBookName(BookName);
        if(category != null)  BookDto.setCategory(category);
        if(file != null)  BookDto.setImage(file);
        if(description != null)  BookDto.setDescription(description);
        System.out.println(BookDto);
        return  new ResponseEntity<>(BookService.update(BookDto,id) , HttpStatus.OK);
    }

    @GetMapping("/Book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) throws UserNotFoundException {
        try {
            return new ResponseEntity<>(BookService.findById(id) , HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @DeleteMapping("/Book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id ){
        try{
            BookService.delete(id);
            return new ResponseEntity<>(new CartMessage("Da Xoa Thanh Cong " ), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new CartMessage("Co loi xay ra") , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Book/same-category/{id}")
    public ResponseEntity<?> getSameCategory(@PathVariable int id , @RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" , defaultValue = "4") int size) throws UserNotFoundException {
            Book Book = BookService.findById(id);
            BookView BookView = new BookView();
            BookView.setBookList(BookService.getSameCategory(Book.getCategory().getCategoryId() , page , size).getContent());
            BookView.setPageCount(BookService.getSameCategory(Book.getCategory().getCategoryId() , page , size).getTotalPages());
        return new ResponseEntity<>(BookView, HttpStatus.OK);
    }

    @GetMapping("/Book/lastestBook")
    public ResponseEntity<?> getLastestBook(){
        Pageable pageable1 = PageRequest.of(0,6);
            return new ResponseEntity<>(BookService.getLastestBook(pageable1) , HttpStatus.OK);
    }

    @GetMapping("/produt/BookSale")
    public ResponseEntity<?> getBookSale(){
            return new ResponseEntity<>(BookService.getBookSale(),HttpStatus.OK);
    }

    @GetMapping("/Book/getAll")
    public ResponseEntity<?> getAll(){
            return new ResponseEntity<>(BookService.getAll(),HttpStatus.OK);
    }
}
