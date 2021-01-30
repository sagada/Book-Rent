package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.BookStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SaveBookResponse {

    private String name;
    private String isbn;
    private String imgUrl;
    private String publisher;
    private String author;
    private BookStatus bookStatus;
    private Long orderId;

    public SaveBookResponse(
            String name, String isbn, String imgUrl, String publisher, String author, BookStatus bookStatus, Long orderId) {
        this.name = name;
        this.isbn = isbn;
        this.imgUrl = imgUrl;
        this.publisher = publisher;
        this.author = author;
        this.bookStatus = bookStatus;
        this.orderId = orderId;
    }
}
