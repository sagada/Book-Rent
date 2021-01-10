package com.library.rent.web.order.service;

import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.domain.OrderStatus;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.library.rent.web.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;
    public Page<OrdersResponse> getReadyBooks(OrderSearchRequest bookSearchCond)
    {
        Page<Order> orders = orderRepository.searchReadyBookWithPaging(bookSearchCond);

        List<OrdersResponse> ordersResponses = orders.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses, orders.getPageable(), orders.getTotalElements());
    }

    @Transactional
    public Long modifyOrderStatus(Long orderId, OrderStatus orderStatus)
    {
        Order order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new GlobalApiException(NOT_FOUND_RESOURCE, orderId + "의 해당하는 주문이 없습니다."));

        if (!CollectionUtils.isEmpty(order.getOrderBookList()))
        {
            updateBookStatus(orderStatus, getBookIds(order));
        }

        order.setOrderStatus(orderStatus);

        return orderId;
    }

    private void updateBookStatus(OrderStatus orderStatus, List<Long> modifyBookIds)
    {
        BookStatus modifyBookStatus = orderStatus == OrderStatus.CANCEL? BookStatus.CANCEL : BookStatus.COMP;
        bookRepository.updateBookStatus(modifyBookStatus, modifyBookIds);
    }

    private List<Long> getBookIds(Order order)
    {
        return order.getOrderBookList()
                .stream()
                .map(orderBook -> orderBook.getBook().getId())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrderBook(Long orderBookId)
    {
        OrderBook orderBook = orderBookRepository.findOrderBookById(orderBookId)
                .orElseThrow(() -> new GlobalApiException(LOGIC_ERROR, "없는 주문 책 ID 입니다."));
        orderBookRepository.delete(orderBook);
    }
}
