package com.library.rent.web.book.controller;

import com.library.rent.web.book.dto.*;
import com.library.rent.web.book.service.BookService;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @PostMapping("/kakao")
    public ResponseEntity<Void> saveBook(@RequestBody @Valid BookDto.SetBookDto param)
    {
        bookService.orderBook(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(@Valid BookSearchRequest bookSearchRequest)
    {
        return bookService.getSavedBook(bookSearchRequest);
    }
}
