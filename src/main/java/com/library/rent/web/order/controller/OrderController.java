package com.library.rent.web.order.controller;

import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrdersResponse>> getOrders(@Valid OrderSearchRequest cond)
    {
        Page<OrdersResponse> results = orderService.getReadyBooks(cond);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
