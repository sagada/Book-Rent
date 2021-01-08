package com.library.rent.web.order.repository;

import com.library.rent.web.order.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    @Query("select ob from OrderBook ob where ob.order.id =: orderId")
    List<OrderBook> findOrderBookByOrderId(Long orderId);
}
