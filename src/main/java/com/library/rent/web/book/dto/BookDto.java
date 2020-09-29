package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.Book;
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
    @Builder
    public static class SearchBooksParam
    {
        private String name;
        private String isbn;
        private int count;
        private String publisher;

        public SearchBooksParam(String name, String isbn, int count, String publisher) {
            this.name = name;
            this.isbn = isbn;
            this.count = count;
            this.publisher = publisher;
        }
    }
}
