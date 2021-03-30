package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.Isbn;
import com.library.rent.web.book.dto.*;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.book.repository.IsbnRepository;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final IsbnRepository isbnRepository;

    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(BookSearchRequest bookSearchRequest)
    {
        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);
        return new ResponseEntity<>(saveBookResponses, HttpStatus.OK);
    }

    // TODO : 주문 패키지로 이동 예정
    @Transactional
    public void orderBook(BookDto.SetBookDto param, Member member)
    {
        List<OrderBook> orderBookList = param.getSetBookParamList()
                .stream()
                .map(this::createOrderBook)
                .collect(Collectors.toList());

        Order order = Order.createOrder(orderBookList, member);
        orderRepository.save(order);
    }

    // TODO : 주문 패키지로 이동 예정
    private OrderBook createOrderBook(BookDto.SetBookParam bookParam)
    {
        List<String> isbnStr = bookParam.getIsbnStr();

        if (!isbnRepository.existsByIsbnNmIn(isbnStr))
        {
            OrderBook orderNewBook = createOrderNewBook(bookParam, isbnStr);
            return orderNewBook;
        }
        else
        {
            Book oldBook = bookRepository.findByIsbnList(isbnStr)
                    .orElseThrow(() -> new GlobalApiException(ErrorCode.LOGIC_ERROR));

            return bookParam.newOrderBook(oldBook, bookParam.getQuantity());
        }
    }

    // TODO : 주문 패키지로 이동 예정
    private OrderBook createOrderNewBook(BookDto.SetBookParam bookParam, List<String> isbns)
    {
        List<Isbn> isbnList = isbns.stream().map(Isbn::newIsbn).collect(Collectors.toList());
        OrderBook orderBook = OrderBook.createOrderBook(bookParam.getQuantity());

        Book newBook = bookParam.newBook();

        newBook.addOrderBook(orderBook);
        isbnList.forEach(newBook::addIsbn);

        bookRepository.save(newBook);
        return orderBook;
    }

}
