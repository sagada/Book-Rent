package com.library.rent.web.order.service;

import com.library.rent.web.book.domain.Book;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        List<OrdersResponse> ordersResponses = orders.getTotalElements() == 0 ? new ArrayList<>() : orders.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses, orders.getPageable(), orders.getTotalElements());
    }

    @Transactional
    public Long modifyOrderStatus(Long orderId, OrderStatus orderStatus)
    {
        Order order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new GlobalApiException(
                        NOT_FOUND_RESOURCE, orderId + "의 해당하는 주문이 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
                );

        List<OrderBook> orderBookList = order.getOrderBookList();
        if (!CollectionUtils.isEmpty(orderBookList)) {

            orderBookList.forEach(orderBook ->
            {

                switch (orderStatus)
                {
                    case READY: {
                        orderBook.getBook().setBookStatus(BookStatus.WAIT);
                        break;
                    }
                    case COMPLETE: {
                        orderBook.getBook().setBookStatus(BookStatus.COMP);
                        break;
                    }
                    case CANCEL: {
                        orderBook.getBook().setBookStatus(BookStatus.CANCEL);
                        break;
                    }
                    default :
                        break;
                }
            });

        }

        order.setOrderStatus(orderStatus);

        return orderId;
    }

    @Transactional
    public void deleteOrderBook(Long orderBookId)
    {
        OrderBook orderBook = orderBookRepository.findOrderBookById(orderBookId)
                .orElseThrow(() -> new GlobalApiException(LOGIC_ERROR, "없는 주문 책 ID 입니다.", HttpStatus.INTERNAL_SERVER_ERROR));
        orderBookRepository.delete(orderBook);
    }

    public void stock(Long orderId)
    {
        Order order = orderRepository.findOrderById(orderId)
                .orElseThrow(()->  new GlobalApiException(NOT_FOUND_RESOURCE, "존재 하지 않는 주문 ID"
                ,HttpStatus.BAD_REQUEST));

        order.stockOrder();
    }

}
