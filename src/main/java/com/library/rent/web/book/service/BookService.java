package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.*;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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

    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(BookSearchRequest bookSearchRequest) {

        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        return new ResponseEntity<>(saveBookResponses, HttpStatus.OK);
    }

    @Transactional
    public void orderBook(BookDto.SetBookDto param, Member member)
    {
        checkDuplicateIsbns(param);
        List<Book> books = createBook(param);

        List<OrderBook> orderBooks = createOrderBookList(books);

        Order order = Order.createOrder(orderBooks, member);
        orderRepository.save(order);
    }

    private List<OrderBook> createOrderBookList(List<Book> books)
    {
        return books.stream()
                .map(book -> OrderBook.createOrderBook(book, book.getQuantity()))
                .collect(Collectors.toList());
    }

    private void checkDuplicateIsbns(BookDto.SetBookDto param)
    {
        List<String> isbnList = getIsbnList(param);
        List<Book> findBookByIsbnList = bookRepository.findBooksByIsbnIn(isbnList);

        if (!CollectionUtils.isEmpty(findBookByIsbnList)) {
            String errorBookList = findBookByIsbnList
                    .stream()
                    .map(Book::getName)
                    .collect(Collectors.joining(",\n"));

            throw new GlobalApiException(ErrorCode.DUPLICATE_BOOK, errorBookList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> getIsbnList(BookDto.SetBookDto param) {
        return param.getSetBookParamList().stream()
                .map(BookDto.SetBookParam::getIsbn)
                .collect(Collectors.toList());
    }

    private List<Book> createBook(BookDto.SetBookDto param) {
        return param.getSetBookParamList().stream()
                .map(BookDto.SetBookParam::createReadyBook)
                .collect(Collectors.toList());
    }
}
