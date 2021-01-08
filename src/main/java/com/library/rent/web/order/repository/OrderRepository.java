package com.library.rent.web.order.repository;

import com.library.rent.web.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{
}
