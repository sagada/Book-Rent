package com.library.rent.web.book.controller;

import com.library.rent.web.book.service.BookFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookFeignController {

    private final BookFeignService bookFeignService;

    @GetMapping("/feign/kakao-book-search")
    String getBookFromKakaoOpenApi(
            @RequestParam(value = "size", defaultValue = "10") int size
            , @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "sort", defaultValue = "accuracy") String sort
            , @RequestParam(value = "query", required = true) String query
            , @RequestParam(value = "target", defaultValue = "title") String target)
    {
        return bookFeignService.getBookFromKakaoOpenApi(size, page, sort, query, target);
    }
}
