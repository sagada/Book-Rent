package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.domain.ISBN;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookDto {

    private BookDto() {
        throw new AssertionError("DTO outer class");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SetBookParam {

        @NotBlank(message = "책 이름은 필수 값 입니다.")
        private String name;

        @NotBlank(message = "책 출판사는 필수 값 입니다.")
        private String publisher;

        private String isbn;

        @Min(value = 1, message = "최소 수량은 1개 입니다.")
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

        public Book newBook()
        {
            return Book.builder()
                    .name(getName())
                    .author(getAuthor())
                    .imgUrl(getImgUrl())
                    .publisher(getPublisher())
                    .quantity(0)
                    .build();
        }

        public Book createReadyBook() {
            List<ISBN> isbnList = Arrays.stream(isbn.split(" ")).map(ISBN::new).collect(Collectors.toList());
            return Book.builder()
                    .quantity(quantity)
                    .imgUrl(imgUrl)
                    .isbns(isbnList)
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
        @NotEmpty
        List<@Valid SetBookParam> setBookParamList = new ArrayList<>();
    }

}
