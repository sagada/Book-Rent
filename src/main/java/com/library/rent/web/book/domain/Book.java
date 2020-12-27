package com.library.rent.web.book.domain;

import com.library.rent.web.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table( indexes = {
        @Index(name = "isbn_idx", columnList = "isbn"),
        @Index(name = "name_idx", columnList = "book_name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "isbn", "publisher", "quantity", "bookStatus"})
@Entity
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="book_name")
    private String name;

    @Column(unique = true)
    private String isbn;

    private int quantity;
    private String publisher;
    private String author;

    @Column(columnDefinition = "TEXT", name= "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public Book(String name)
    {
        this.name = name;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
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

}
