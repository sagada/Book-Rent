package com.library.rent.web.order.repository;

import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static com.library.rent.web.order.domain.QOrder.order;


public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager entityManager)
    {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Order> searchReadyBookWithPaging(OrderSearchRequest cond)
    {
        PageRequest pageable = PageRequest.of(cond.getPage(), cond.getSize());

        QueryResults<Order> results = queryFactory
                .selectFrom(order)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .where(
                        orderIdEq(cond.getOrderId()),
                        orderStatusEq(cond.getOrderStatus()),
                        orderLoe(cond.getStartDt()), orderGoe(cond.getEndDt())
                )
                .orderBy(order.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());

    }

    private BooleanExpression orderIdEq(Long orderId)
    {
        return orderId != null ? order.id.eq(orderId) : null;
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus)
    {
        return orderStatus != null ? order.orderStatus.eq(orderStatus) : null;
    }

    private BooleanExpression orderGoe(LocalDateTime endDt)
    {
        return endDt != null ? order.orderDate.loe(endDt) : null;
    }

    private BooleanExpression orderLoe(LocalDateTime startDt)
    {
        return startDt != null ? order.orderDate.goe(startDt) : null;
    }

}
