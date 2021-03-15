package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.BookStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SaveBookResponse {

    private String name;
    private String imgUrl;
    private String publisher;
    private String author;
    private Long orderId;

    public SaveBookResponse(
            String name, String imgUrl, String publisher, String author, Long orderId) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.publisher = publisher;
        this.author = author;
        this.orderId = orderId;
    }
}
