package com.library.rent.web.book.domain;

import com.library.rent.web.rentbook.domain.RentBook;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Table( indexes = {
            @Index(name = "isbn_idx", columnList = "isbn"),
            @Index(name = "name_idx", columnList = "book_name")
        }
)
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="book_name")
    private String name;

    private String isbn;

    private int quantity;
    private String publisher;
    private String author;

    @Column(columnDefinition = "TEXT", name= "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @Builder
    public Book(String name, String isbn, int quantity, String publisher, String author, String imgUrl, BookStatus bookStatus) {
        this.name = name;
        this.isbn = isbn;
        this.quantity = quantity;
        this.publisher = publisher;
        this.author = author;
        this.imgUrl = imgUrl;
        this.bookStatus = bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}
