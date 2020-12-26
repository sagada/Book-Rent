//package com.library.rent.web.order;
//
//import com.library.rent.web.member.domain.Member;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;
//
//    private LocalDateTime orderDate;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//
//    @OneToMany()
//    List<OrderBook> orderBookList = new ArrayList<>();
//}
