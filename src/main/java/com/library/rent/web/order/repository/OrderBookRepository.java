package com.library.rent.web.order.repository;

import com.library.rent.web.order.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
