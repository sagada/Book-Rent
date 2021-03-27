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

import java.util.ArrayList;
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

    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(BookSearchRequest bookSearchRequest) {

        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        return new ResponseEntity<>(saveBookResponses, HttpStatus.OK);
    }

    @Transactional
    public void orderBook(BookDto.SetBookDto param, Member member)
    {
        List<OrderBook> orderBookList = new ArrayList<>();

        for (BookDto.SetBookParam bookParam : param.getSetBookParamList())
        {
            validateSaveBook(bookParam);

            List<String> isbns = Arrays.asList(bookParam.getIsbn().split(" "));

            if (!isbnRepository.existsByIsbnIn(isbns))
            {
                createNewBook(orderBookList, bookParam, isbns);
            }
            else
            {
                List<ISBN> isbnList = isbns.stream().map(ISBN::new).collect(Collectors.toList());

                Book oldBook = bookRepository.findByIsbnList(isbns).orElseGet(
                        ()-> Book.createWaitBook(bookParam.getName(),bookParam.getQuantity(), isbnList)
                );
                bookRepository.save(oldBook);
            }
        }

        Order order = Order.createOrder(orderBookList, member);
        orderRepository.save(order);
    }

    private void createNewBook(List<OrderBook> orderBookList, BookDto.SetBookParam bookParam, List<String> isbns)
    {
        List<ISBN> isbnList = isbns.stream().map(ISBN::new).collect(Collectors.toList());
        Book newBook = bookParam.newBook();
        newBook.setIsbns(isbnList);
        OrderBook orderBook = OrderBook.createOrderBook(bookParam.getQuantity());
        orderBook.setBook(newBook);
        bookRepository.save(newBook);
        orderBookList.add(orderBook);
    }

    private void validateSaveBook(BookDto.SetBookParam bookParam) {
        if (bookParam.getIsbn().isEmpty())
        {
            throw new GlobalApiException(
                    ErrorCode.PARAMETER_ERROR
                    , "ISBN이 존재하지 않는 책은 등록이 불가능 합니다."
                    ,HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
