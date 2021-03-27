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
import com.library.rent.web.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final IsbnRepository isbnRepository;

    public ResponseEntity<Page<SaveBookResponse>> getSavedBook(BookSearchRequest bookSearchRequest) {

        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        return new ResponseEntity<>(saveBookResponses, HttpStatus.OK);
    }

    @Transactional
    public void orderBook(BookDto.SetBookDto param, Member member)
    {
        List<Book> books = new ArrayList<>();

        for (BookDto.SetBookParam bookParam : param.getSetBookParamList())
        {
            if (bookParam.getIsbn().isEmpty())
            {
                throw new GlobalApiException(
                        ErrorCode.PARAMETER_ERROR
                        , "ISBN이 존재하지 않는 책은 등록이 불가능 합니다."
                        ,HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

            List<String> isbns = Arrays.asList(bookParam.getIsbn().split(" "));

            if (!isbnRepository.existsByIsbnIn(isbns))
            {
                List<ISBN> isbnList = isbns.stream().map(ISBN::new).collect(Collectors.toList());
                isbnRepository.saveAll(isbnList);
                Book newBook = bookParam.newBook();;
                newBook.setIsbns(isbnList);
                bookRepository.save(newBook);
                books.add(newBook);
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

}
