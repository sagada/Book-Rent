package com.library.rent.web.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.rent.web.book.domain.Book;
import lombok.*;

import java.util.List;

public class BookDto {

    private BookDto()
    {
        throw new AssertionError("DTO outer class");
    }

    @Getter
    @Setter
    public static class SetBooksXlsxParam
    {
        private Long id;
        private String name;
        private Long isbn;
        private int count;
        private String publisher;
    }

    @Getter
    @Setter
    @Builder
    public static class BookInfo
    {
        private Long id;
        private String name;
        private String isbn;
        private int count;
        private String publisher;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class SearchBooksParam
    {
        private String name;
        private String isbn;
        private int count = 0;
        private String publisher;

        @Builder
        public SearchBooksParam(String name, String isbn, int count, String publisher)
        {
            this.name = name;
            this.isbn = isbn;
            this.count = count;
            this.publisher = publisher;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BookQuantityResponse
    {
        private String isbn;
        private int quantity;

        @Builder
        public BookQuantityResponse(String isbn, int quantity) {
            this.isbn = isbn;
            this.quantity = quantity;
        }

        public static BookQuantityResponse createEmptyResponse(String isbn)
        {
            return BookQuantityResponse.builder().isbn(isbn).quantity(0).build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SetBookParam
    {
        private String name;
        private String publisher;
        private String isbn;
        private int quantity;
        private String imgUrl;
        private String author;

        @Builder
        public SetBookParam(String name, String publisher, String isbn, int quantity, String imgUrl, String author) {
            this.name = name;
            this.publisher = publisher;
            this.isbn = isbn;
            this.quantity = quantity;
            this.imgUrl = imgUrl;
            this.author = author;
        }

        public Book toEntity()
        {
            return Book.builder()
                    .quantity(quantity)
                    .imgUrl(imgUrl)
                    .isbn(isbn)
                    .name(name)
                    .publisher(publisher)
                    .author(author)
                    .build();
        }

    }


}
