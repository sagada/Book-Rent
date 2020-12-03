package com.library.rent.web.book.controller;

import com.library.rent.util.page.PageRequest;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping("/xlsx/books")
    public void setBooksXlsx(@RequestBody BookDto.SetBooksXlsxParam param)
    {
        bookService.setBooksXlsx(param);
    }

    @GetMapping("/admin")
    public List<BookDto.BookInfo> getBooksAdmin(BookDto.SearchBooksParam param)
    {
        return bookService.getBooksAdmin(param);
    }

    @GetMapping("/check/quantity")
    public Map<String, Integer> getBookQuantity(@RequestParam(value = "isbns") String isbns) throws Exception
    {
        List<String> isbnList = validateAndMakeIsbnList(isbns);
        return bookService.getBookQuantity(isbnList);
    }

    private List<String> validateAndMakeIsbnList(String isbns) throws Exception {

        if (isbns.trim().isEmpty())
            throw new Exception("파라미터 에러");

        List<String> parsing = Arrays.asList(isbns.split(",").clone());
        return parsing.stream().map(String::trim).collect(Collectors.toList());
    }

    @GetMapping("/kakao/search")
    public List<BookDto.BookInfo> getBooks(
            PageRequest pageRequest
            ,BookDto.SearchBooksParam param)
    {
        return bookService.getBooks(pageRequest.of(), param);
    }

    @PostMapping("/kakao")
    public void setBook(@RequestBody List<BookDto.SetBookParam> param)
    {
        bookService.setBook(param);
    }

}
