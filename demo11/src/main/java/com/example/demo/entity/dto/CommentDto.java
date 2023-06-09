package com.example.demo.entity.dto;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @NotBlank(message = "content should not be blank!")
    @NotNull(message = "content should not be null!")
    private String content;
    private int  BookId;
    private int userId;
}
