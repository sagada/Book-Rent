package com.library.rent.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.order.Order;
import com.library.rent.web.order.OrderBook;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.library.rent.web.order.service.OrderService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        for (int i = 0 ;i < 15; i++) {
            Book newbook = new Book();
            newbook.setBookStatus(BookStatus.WAIT);
            newbook.setName("bookName " + i);
            newbook.setIsbn("126163L" + i);

            Book newbook2 = new Book();
            newbook2.setBookStatus(BookStatus.WAIT);
            newbook2.setName("bookName2 " + i);
            newbook2.setIsbn("1223223L"+ i);

            OrderBook orderBook1 = OrderBook.createOrderBook(newbook, 10);
            OrderBook orderBook2 = OrderBook.createOrderBook(newbook2, 12);
            Order order2 = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2));

            orderRepository.save(order2);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void orderSearchTest()
    {
        OrderSearchRequest readyBookSearchCond =  new OrderSearchRequest();
        readyBookSearchCond.setPage(0);
        readyBookSearchCond.setSize(10);

        Page<OrdersResponse> result = orderService.getReadyBooks(readyBookSearchCond);
        for (OrdersResponse ordersResponse : result.getContent())
        {
            System.out.println();
            System.out.println("ordersResponse.getOrderId() = " + ordersResponse.getOrderId());
            System.out.println("ordersResponse.getOrderStatus() = " +  ordersResponse.getOrderStatus());

            for (OrdersResponse.OrderBookDto orderBookDto : ordersResponse.getOrderBookDtoList())
            {
                System.out.println("orderBookDto Id = " + orderBookDto.getOrderBookId());
                System.out.println("book Name = " + orderBookDto.getBookName());
                System.out.println("book isbn = " + orderBookDto.getIsbn());
            }
        }
        Assertions.assertThat(result.getSize()).isEqualTo(10);
    }
}
