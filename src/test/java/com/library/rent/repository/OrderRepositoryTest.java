package com.library.rent.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    BookRepository bookRepository;

    JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void init()
    {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
        for (int i = 0 ;i < 50; i++) {
            Book newbook = new Book();
            newbook.setBookStatus(BookStatus.WAIT);
            newbook.setIsbn("126163L" + i);
            bookRepository.save(newbook);

            Book newbook2 = new Book();
            newbook2.setBookStatus(BookStatus.WAIT);
            newbook2.setIsbn("1223223L"+ i);
            bookRepository.save(newbook2);

            OrderBook orderBook1 = OrderBook.createOrderBook(newbook, 10);
            OrderBook orderBook2 = OrderBook.createOrderBook(newbook2, 12);

            orderBookRepository.save(orderBook1);
            orderBookRepository.save(orderBook2);
            Order order2 = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2));

            orderRepository.save(order2);
        }
    }

    @Test
    public void getOrderListTest()
    {
        OrderSearchRequest readyBookSearchCond =  new OrderSearchRequest();
        readyBookSearchCond.setPage(0);
        readyBookSearchCond.setSize(15);

        List<Order> orders = orderRepository.searchReadyBookWithPaging(readyBookSearchCond);

        assertThat(orders.size()).isEqualTo(10);
    }
}
