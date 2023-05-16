package com.example.demo.entity.dto;

import com.example.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookView {
    private int pageCount;
    private List<Book> BookList;
}
