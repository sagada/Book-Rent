package com.library.rent.web.book.controller;

import com.library.rent.web.book.dto.*;
import com.library.rent.web.book.service.BookService;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
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
    private final MemberService memberService;


    @Autowired
    public BookController(BookService bookService, MemberService memberService)
    {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @PostMapping("/kakao")
    public ResponseEntity<Void> setBook(@RequestBody @Valid BookDto.SetBookDto param)
    {
        Member member = memberService.findByEmail();
        bookService.orderBook(param, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(@Valid BookSearchRequest bookSearchRequest)
    {
        return bookService.getSavedBook(bookSearchRequest);
    }
}
