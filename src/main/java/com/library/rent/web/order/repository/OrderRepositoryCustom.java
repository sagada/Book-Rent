package com.library.rent.web.order.repository;

import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.domain.Order;


import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> searchReadyBookWithPaging(OrderSearchRequest readyBookSearchCond);
}
