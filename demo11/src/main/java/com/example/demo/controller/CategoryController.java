package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Book;
import com.example.demo.entity.dto.CartMessage;
import com.example.demo.entity.dto.CategoryDto;
import com.example.demo.entity.dto.CategoryView;
import com.example.demo.entity.dto.BookView;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.service.CategoryService;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private BookService BookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    public ResponseEntity<?> getCategories(@RequestParam(name = "size" , required = false , defaultValue = "6") int size , @RequestParam(name = "page" , required = false , defaultValue = "1") int page){
        List<Category> categories = categoryService.findAll(page,size).getContent();
        int pageCount = categoryService.findAll(page,size).getTotalPages();
        return new ResponseEntity<>(new CategoryView(pageCount,categories),HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id){
        return new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(categoryService.getAll(),HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDto categoryDto) throws ObjectExistedException {
        return new ResponseEntity<>(categoryService.create(categoryDto) , HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDto categoryDto , @PathVariable int id) throws ObjectExistedException {
        return new ResponseEntity<>(categoryService.update(categoryDto,id),HttpStatus.OK);
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        categoryService.delete(id);
        return new ResponseEntity<>(new CartMessage("delete successfully"),HttpStatus.OK);
    }
    @GetMapping("/category/Book/{id}")
    public ResponseEntity<?> getBookByCategoryId(@PathVariable int id , @RequestParam(name = "page" , defaultValue = "1") int page , @RequestParam(name = "size" , defaultValue = "6") int size){
        List<Book> list = BookService.getBookByCategoryId(id , page, size).getContent();
        int pageCount = BookService.getBookByCategoryId(id , page, size).getTotalPages();
        BookView BookView = new BookView();
        BookView.setBookList(list);
        BookView.setPageCount(pageCount);
        return new ResponseEntity<>(BookView,HttpStatus.OK);
    }
}
