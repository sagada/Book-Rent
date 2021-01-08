package com.library.rent.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.library.rent.web.order.service.OrderService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    JPAQueryFactory jpaQueryFactory;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void init()
    {
        jpaQueryFactory = new JPAQueryFactory(entityManager);

        for (int i = 0; i < 20; i++)
        {
            Book firstBook  = Book.createWaitBook("firstBook"  + i, 2, "firstIsbn"  + i);
            Book secondBook = Book.createWaitBook("secondBook" + i, 1, "secondIsbn" + i);

            OrderBook orderBook1 = OrderBook.createOrderBook(firstBook, 10);
            OrderBook orderBook2 = OrderBook.createOrderBook(secondBook, 12);
            Order newOrder = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2));
            orderRepository.save(newOrder);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void orderSearchTest()
    {
        // given
        OrderSearchRequest readyBookSearchCond =  new OrderSearchRequest();
        readyBookSearchCond.setPage(0);
        readyBookSearchCond.setSize(10);

        // when
        Page<OrdersResponse> result = orderService.getReadyBooks(readyBookSearchCond);
        List<OrdersResponse.OrderBookDto> allOrderBookDtoList = result
                .getContent()
                .stream()
                .map(OrdersResponse::getOrderBookDtoList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(allOrderBookDtoList.size()).isEqualTo(20);
        assertThat(allOrderBookDtoList).extracting("bookStatus").containsOnly(BookStatus.WAIT);
        assertThat(result.getNumber()).isEqualTo(0);
    }
}
