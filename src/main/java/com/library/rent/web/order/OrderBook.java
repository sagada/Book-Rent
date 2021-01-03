package com.library.rent.web.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.rent.web.BaseEntity;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"count"})
@Getter
@Table(name = "orders_book")
@Entity
public class OrderBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_book_id")
    private Long id;

    private int count;

    @JsonIgnore
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @JsonIgnore
    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Book book;

    public OrderBook(int count, Book book) {
        this.count = count;
        this.book = book;
    }

    public static OrderBook createOrderBook(Book book, int count)
    {
        OrderBook orderBook = new OrderBook(count, book);
        return orderBook;
    }

}
