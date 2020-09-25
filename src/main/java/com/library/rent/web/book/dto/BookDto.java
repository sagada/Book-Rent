package com.library.rent.web.book.dto;

import lombok.Getter;
import lombok.Setter;

public class BookDto {

    private BookDto()
    {
        throw new AssertionError("DTO outer class");
    }

    @Getter
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
    public static class BookInfo
    {
        private Long id;
        private String name;
        private Long isbn;
        private int count;
        private String publisher;
    }
    @Getter
    public static class SearchBooksParam
    {
        private String name;
        private Long isbn;
        private int count;
        private String publisher;
    }
}
