package com.library.rent.service;

import com.library.rent.web.auth.Authority;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.domain.ISBN;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.repository.MemberRepository;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.library.rent.web.order.service.OrderService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    JPAQueryFactory jpaQueryFactory;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Test
    public void orderSearchTest() {
        // given
        OrderSearchRequest readyBookSearchCond = new OrderSearchRequest();
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

    @Test
    @Transactional
    public void memberOrderTest()
    {
        // given
        String memberEmail = "admin@naver.com";
        Member member = new Member(memberEmail, "admin", "nick", true);
        memberRepository.save(member);
        ISBN isbn1= new ISBN();
        ISBN isbn2= new ISBN();
        Book b1 = Book.createWaitBook("book1", 1, Lists.newArrayList(isbn1));
        Book b2 = Book.createWaitBook("book2", 1, Lists.newArrayList(isbn2));

        OrderBook orderBook1 = OrderBook.createOrderBook(b1, 1);
        OrderBook orderBook2 = OrderBook.createOrderBook(b2, 1);

        Order order = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2), member);
        Order saveOrder = orderRepository.save(order);

        assertThat(saveOrder.getMember().getEmail()).isEqualTo(memberEmail);
        assertThat(order.getOrderBookList().size()).isEqualTo(2);
    }


    @Test
    @Commit
    public void adminTest()
    {
        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        Member user = Member.builder()
                .email("admin@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("admin")
                .authorities(Collections.singleton(authority))
                .active(true)
                .build();

        memberRepository.save(user);
    }
}
