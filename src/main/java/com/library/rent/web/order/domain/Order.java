package com.library.rent.web.order.domain;


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

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addOrderBook(OrderBook orderBook)
    {
        orderBookList.add(orderBook);
        orderBook.setOrder(this);
    }

    public static Order createOrder(List<OrderBook> orderBooks)
    {
        Order order = new Order();

        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.READY);
        orderBooks.forEach(order::addOrderBook);
        return order;
    }

}
