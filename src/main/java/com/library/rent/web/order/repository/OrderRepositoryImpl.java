package com.library.rent.web.order.repository;

import com.library.rent.web.order.domain.QOrder;
import com.library.rent.web.order.dto.OrderSearchRequest;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.library.rent.web.order.domain.QOrder.order;


public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager entityManager)
    {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Order> searchReadyBookWithPaging(OrderSearchRequest cond)
    {
        PageRequest pageable = PageRequest.of(cond.getPage(), cond.getSize());

        return queryFactory
                .selectFrom(order)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .where(
                        orderDateEq(cond.getOrderDate()),
                        orderStatusEq(cond.getOrderStatus()),
                        orderLoe(cond.getStartDt()) , orderGoe(cond.getEndDt())
                )
                .orderBy(order.id.desc())
                .fetch();
    }

    private BooleanExpression orderDateEq(LocalDateTime orderDate)
    {
        return orderDate != null ? order.orderDate.eq(orderDate) : null;
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
