package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.Isbn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class SaveBookResponse {

    private String name;
    private String imgUrl;
    private String publisher;
    private String author;
    private Long orderId;
    private String isbnNm;

    @Builder
    public SaveBookResponse(String name, String imgUrl, String publisher, String author, Long orderId, String isbnNm) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.publisher = publisher;
        this.author = author;
        this.orderId = orderId;
        this.isbnNm = isbnNm;
    }
}
