package com.library.rent.repository;

import com.google.common.collect.Lists;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.domain.Isbn;
import com.library.rent.web.book.dto.BookSearchRequest;
import com.library.rent.web.book.dto.SaveBookResponse;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.book.repository.IsbnRepository;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    JPAQueryFactory queryFactory;

    @Autowired
    IsbnRepository isbnRepository;

    @Test
    void createOneBookTest() {
        String bookName = "JPA";
        Book book = new Book(bookName);
        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isEqualTo(book);
        assertThat(savedBook.getName()).isEqualTo(bookName);
    }

    @Test
    void bookIsbnTest() {
        String bookName = "JPA";
        Book book1 = new Book(bookName);
        Isbn isbn1 = new Isbn("isbn1");
        isbn1.setBook(book1);
        book1.getIsbnList().add(isbn1);

        Book newBook = bookRepository.save(book1);
        assertThat(newBook.getIsbnList().size()).isEqualTo(1);
        assertThat(newBook.getIsbnList().get(0).getIsbnNm()).isEqualTo("isbn1");
    }

    //
    @Test
    public void saveBookSearchAllNullParamTest() {
        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
                .page(0)
                .size(10)
                .build();
        // when
        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        // then
        assertThat(saveBookResponses.getContent().size()).isEqualTo(10);
    }

    @Test
    public void saveBookSearchByAuthor() {
        // given
        String author = "베놈";
        Book book = Book.builder()
                .author(author)
                .name("톰하디")
                .build();

        entityManager.persist(book);
        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
                .search(author)
                .bookSearchType(BookSearchType.AUTHOR)
                .page(0)
                .size(10)
                .build();

        // when
        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        // then
        assertThat(saveBookResponses.getContent().size()).isEqualTo(1);
        assertThat(saveBookResponses.getContent().get(0).getAuthor()).isEqualTo(author);
    }

    private List<Book> givenMockWaitBooks() {
        return Lists.newArrayList(
                Book.builder()
                        .author("아이유")
                        .name("JPA")
                        .publisher("동아출판사")
                        .bookStatus(BookStatus.WAIT)
                        .build(),

                Book.builder()
                        .author("이동민")
                        .name("JPA와 함께하는 JPA 프로그래밍")
                        .publisher("동아출판사")
                        .bookStatus(BookStatus.WAIT)
                        .build(),

                Book.builder()
                        .author("토비")
                        .name("스프링부트")
                        .publisher("동아출판사")
                        .bookStatus(BookStatus.WAIT)
                        .build()
        );
    }
}