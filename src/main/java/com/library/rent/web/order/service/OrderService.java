package com.library.rent.web.order.service;

import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.Order;
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrdersResponse> getReadyBooks(OrderSearchRequest bookSearchCond)
    {

        Page<Order> orders = orderRepository.searchReadyBookWithPaging(bookSearchCond);

        List<OrdersResponse> ordersResponses = orders.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses
                , PageRequest.of(bookSearchCond.getPage(), bookSearchCond.getSize())
                , orders.getTotalElements());

    }
}
