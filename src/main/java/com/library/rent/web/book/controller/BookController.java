package com.library.rent.web.book.controller;

import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/book")
@Log4j2
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @PostMapping("/kakao")
    public void setBook(@RequestBody BookDto.SetBookDto param)
    {
        bookService.setBook(param);
    }

}
