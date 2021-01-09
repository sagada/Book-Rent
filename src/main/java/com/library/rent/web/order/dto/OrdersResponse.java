package com.library.rent.web.order.dto;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.domain.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrdersResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderBookDto> orderBookDtoList = new ArrayList<>();

    public OrdersResponse(Order order)
    {
        orderDate = order.getOrderDate();
        orderId = order.getId();
        orderStatus = order.getOrderStatus();
        orderBookDtoList = order.getOrderBookList()
                .stream()
                .map(orderBook -> new OrderBookDto(orderBook, orderBook.getBook()))
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class OrderBookDto
    {
        private Long orderBookId;
        private String bookName;
        private int quantity;
        private String isbn;
        private String author;
        private BookStatus bookStatus;
        private String publisher;
        private Long bookId;
        private String imgUrl;
        public OrderBookDto(OrderBook orderBook, Book book)
        {
            this.imgUrl = book.getImgUrl();
            this.orderBookId = orderBook.getId();
            this.bookId = book.getId();
            this.bookName = book.getName();
            this.quantity = book.getQuantity();
            this.isbn = book.getIsbn();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.bookStatus = book.getBookStatus();
        }
    }

}
