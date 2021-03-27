package com.library.rent.web.order.domain;

import com.library.rent.web.BaseEntity;
import com.library.rent.web.book.domain.Book;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"count", "id"})
@Getter
@Table(name = "orders_book")
@Entity
public class OrderBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_book_id")
    private Long id;

    private int count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }

    public OrderBook(int count) {
        this.count = count;
    }

    public static OrderBook createOrderBook(int count) {
        return new OrderBook(count);
    }

}
