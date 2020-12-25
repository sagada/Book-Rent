package com.library.rent.web.book.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table( indexes = {@Index(name = "isbn_idx", columnList = "isbn"), @Index(name = "name_idx", columnList = "book_name")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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

    @Column(name = "reg_dt")
    @CreationTimestamp
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "chg_dt")
    private LocalDateTime chgDt;

    public Book(String name) {
        this.name = name;
    }

    @Builder
    public Book(
            String name
            , String isbn
            , int quantity
            , String publisher
            , String author
            , String imgUrl
            , BookStatus bookStatus)
    {
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
