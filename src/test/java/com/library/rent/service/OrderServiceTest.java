package com.library.rent.service;

import com.library.rent.web.auth.Authority;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.repository.MemberRepository;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.library.rent.web.order.service.OrderService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

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
