package com.library.rent.web.book.domain;

import com.library.rent.web.BaseEntity;
import com.library.rent.web.order.domain.OrderBook;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(indexes = {
        @Index(name = "isbn_idx", columnList = "isbn"),
        @Index(name = "name_idx", columnList = "book_name")
})
@NoArgsConstructor
@ToString(of = {"name", "isbn", "publisher", "quantity", "bookStatus"})
@Entity
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "isbn")
    private String isbn;

    private int quantity;
    private String publisher;
    private String author;

    @Column(columnDefinition = "TEXT", name = "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @OneToMany(mappedBy = "book")
    private List<OrderBook> orderBookList = new ArrayList<>();

    public Book(String name) {
        this.name = name;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public static Book createWaitBook(String name, int quantity, String isbn) {
        return Book.builder().name(name).quantity(quantity).isbn(isbn).bookStatus(BookStatus.WAIT).build();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Builder
    private Book(
            String name
            , String isbn
            , int quantity
            , String publisher
            , String author
            , String imgUrl
            , BookStatus bookStatus) {
        this.name = name;
        this.isbn = isbn;
        this.quantity = quantity;
        this.publisher = publisher;
        this.author = author;
        this.imgUrl = imgUrl;
        this.bookStatus = bookStatus;
    }


}
