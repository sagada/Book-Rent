package com.library.rent.web.order.repository;

import com.library.rent.web.order.domain.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    Optional<OrderBook> findOrderBookById(Long orderBookId);
}
