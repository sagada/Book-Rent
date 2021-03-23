package com.library.rent.web.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.rent.web.BaseEntity;
import com.library.rent.web.book.dto.BookDto;
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
@ToString(of = {"name", "publisher", "quantity"})
@Entity
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String name;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"isbn"})
    private List<ISBN> isbns = new ArrayList<>();

    private int quantity;
    private String publisher;
    private String author;

    @Column(columnDefinition = "TEXT", name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
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

    public static Book createWaitBook(String name, int quantity, List<ISBN> isbnList) {
        return Book.builder().name(name).quantity(quantity).isbns(isbnList).bookStatus(BookStatus.WAIT).build();
    }

    public void setIsbns(List<ISBN> isbns) {
        isbns.forEach(s-> s.setBook(this));
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
            , List<ISBN> isbns
            , int quantity
            , String publisher
            , String author
            , String imgUrl
            , BookStatus bookStatus)
    {
        this.name = name;
        this.quantity = quantity;
        this.publisher = publisher;
        this.isbns = isbns;
        this.author = author;
        this.imgUrl = imgUrl;
    }


}
