package com.library.rent.web.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookSearchCondition {

    private String name;
    private String isbn;
    private String publisher;
    private String author;

    @Builder
    public BookSearchCondition(String name, String isbn, String publisher, String author)
    {
        this.name = name;
        this.isbn = isbn;
        this.publisher = publisher;
        this.author = author;
    }
}
