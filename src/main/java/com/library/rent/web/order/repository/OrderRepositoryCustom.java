package com.library.rent.web.order.repository;

import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.Order;
import org.springframework.data.domain.Page;

public interface OrderRepositoryCustom {
    Page<Order> searchReadyBookWithPaging(OrderSearchRequest readyBookSearchCond);
}
