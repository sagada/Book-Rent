package com.library.rent.web.order.controller;

import com.library.rent.web.order.domain.OrderStatus;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrdersResponse>> getOrders(@Valid OrderSearchRequest cond) {
        Page<OrdersResponse> results = orderService.getReadyBooks(cond);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/modify/{orderId}/{orderStatus}")
    public ResponseEntity<Long> modifyOrderStatus(
            @PathVariable(value = "orderId") Long orderId,
            @PathVariable(value = "orderStatus") OrderStatus orderStatus) {
        return new ResponseEntity<>(orderService.modifyOrderStatus(orderId, orderStatus), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{orderBookId}")
    public ResponseEntity<Void> deleteOrderBook(@PathVariable(value = "orderBookId") Long orderBookId) {
        orderService.deleteOrderBook(orderBookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/stock/{orderId}")
    public ResponseEntity<Void> ok(@PathVariable(value = "orderId") Long orderId)
    {
        orderService.stock(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
