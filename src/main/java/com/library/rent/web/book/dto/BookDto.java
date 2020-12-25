package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class BookDto {

    private BookDto() {
        throw new AssertionError("DTO outer class");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SetBookParam {
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

        public Book toEntity() {
            return Book.builder()
                    .quantity(quantity)
                    .imgUrl(imgUrl)
                    .isbn(isbn)
                    .name(name)
                    .publisher(publisher)
                    .author(author)
                    .bookStatus(BookStatus.WAIT)
                    .build();
        }
    }


    @Getter
    @Setter
    public static class SetBookDto {
        List<SetBookParam> setBookParamList = new ArrayList<>();

        public void validate() {
            setBookParamList.forEach(bookParam ->
            {
                if (bookParam.getIsbn() == null || bookParam.getName() == null || bookParam.getQuantity() == 0) {
                    throw new IllegalStateException("Error Parameter");
                }
            });
        }
    }
}
