package com.library.rent.web.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public static class BookInfo
    {
        private Long id;
        private String name;
        private Long isbn;
        private int count;
        private String publisher;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class SearchBooksParam
    {
        private String name;
        private Long isbn;
        private int count;
        private String publisher;
    }
}
