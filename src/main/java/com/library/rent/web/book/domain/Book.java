package com.library.rent.web.book.domain;

import com.library.rent.web.BaseEntity;
import com.library.rent.web.order.domain.OrderBook;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(indexes = {
        @Index(name = "name_idx", columnList = "book_name")
})
@NoArgsConstructor
@ToString(of = {"name", "publisher", "quantity", "bookStatus"})
@Entity
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String name;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Isbn> isbnList = new ArrayList<>();

    private int quantity;
    private String publisher;
    private String author;

    @Column(columnDefinition = "TEXT", name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OrderBook> orderBookList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public Book(String name) {
        this.name = name;
    }

    public void addIsbn(Isbn isbn)
    {
        this.getIsbnList().add(isbn);
        isbn.setBook(this);
    }

    public void addOrderBook(OrderBook orderBook)
    {
        this.getOrderBookList().add(orderBook);
        orderBook.setBook(this);
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void addQuantity(int count)
    {
        quantity += count;
    }

    @Builder
    private Book(
            String name
            , int quantity
            , String publisher
            , String author
            , String imgUrl
            , BookStatus bookStatus)
    {
        this.name = name;
        this.quantity = quantity;
        this.publisher = publisher;
        this.bookStatus = bookStatus;
        this.author = author;
        this.imgUrl = imgUrl;
    }


}
