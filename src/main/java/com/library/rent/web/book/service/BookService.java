package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.ISBN;
import com.library.rent.web.book.dto.*;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.book.repository.IsbnRepository;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final IsbnRepository isbnRepository;
    private final OrderBookRepository orderBookRepository;

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
        List<String> isbns = bookParam.getIsbnStr();

        if (!isbnRepository.existsByIsbnIn(isbns))
        {
            return createOrderBook(bookParam, isbns);
        }
        else
        {
            Book oldBook = bookRepository.findByIsbnList(isbns)
                    .orElseThrow(
                            () -> new GlobalApiException(
                                    ErrorCode.LOGIC_ERROR
                                    , "로직 에러"
                                    , HttpStatus.INTERNAL_SERVER_ERROR
                            )
                    );

            return bookParam.newOrderBook(oldBook, bookParam.getQuantity());
        }
    }

    private OrderBook createOrderBook(BookDto.SetBookParam bookParam, List<String> isbns)
    {
        List<ISBN> isbnList = isbns.stream().map(ISBN::new).collect(Collectors.toList());
        isbnRepository.saveAll(isbnList);

        Book newBook = bookParam.newBook();
        newBook.setIsbns(isbnList);
        bookRepository.save(newBook);

        OrderBook orderBook = OrderBook.createOrderBook(bookParam.getQuantity());
        orderBook.setBook(newBook);

        return orderBook;
    }

}
