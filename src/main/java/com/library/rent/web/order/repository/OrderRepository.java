package com.library.rent.web.order.repository;

import com.library.rent.web.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findOrderById(Long orderId);
}
