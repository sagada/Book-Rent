package com.library.rent.web.order.domain;


import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderBook> orderBookList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addOrderBook(OrderBook orderBook)
    {
        orderBook.setOrder(this);
        orderBookList.add(orderBook);

    }

    public static Order createOrder(List<OrderBook> orderBooks, Member member)
    {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.READY);
        order.setMember(member);
        orderBooks.forEach(order::addOrderBook);

        return order;
    }

    public void stockOrder()
    {
        this.getOrderBookList().forEach(orderBook -> orderBook.getBook().setBookStatus(BookStatus.COMP));
        setOrderStatus(OrderStatus.COMPLETE);
    }

}
