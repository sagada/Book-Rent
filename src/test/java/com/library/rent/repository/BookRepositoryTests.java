package com.library.rent.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.dto.BookSearchRequest;
import com.library.rent.web.order.dto.OrdersResponse;
import com.library.rent.web.book.dto.SaveBookResponse;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.order.domain.Order;
import com.library.rent.web.order.domain.OrderBook;
import com.library.rent.web.order.repository.OrderBookRepository;
import com.library.rent.web.order.repository.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.library.rent.web.order.QOrder.*;
import static com.library.rent.web.order.QOrderBook.orderBook;
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
    @BeforeEach
    public void init()
    {
        queryFactory = new JPAQueryFactory(entityManager);
//        for (int i = 1; i <= 30; i++)
//        {
//            Book book = Book.builder()
//                    .bookStatus(BookStatus.WAIT)
//                    .author("author" + i)
//                    .imgUrl("imgUrl" + i)
//                    .isbn("isbn" + i)
//                    .name("name" +i)
//                    .publisher("publisher" + i)
//                    .build();
//            entityManager.persist(book);
//            entityManager.flush();
//            entityManager.clear();
//        }
    }

    @Test
    public void createABookTest()
    {
        String bookName = "JPA";
        Book book = new Book(bookName);
        Book savedBook = bookRepository.save(book);

        System.out.println(savedBook);
        assertThat(savedBook).isEqualTo(book);
        assertThat(savedBook.getName()).isEqualTo(bookName);
    }

    @Test
    public void isbnExistTest()
    {
        String bookName = "JPA";
        Book book1 = new Book(bookName);
        book1.setIsbn("isbnA");
        bookRepository.save(book1);

        Book book2 = new Book(bookName);
        book2.setIsbn("isbnB");
        bookRepository.save(book2);

        Book book3 = new Book(bookName);
        book3.setIsbn("isbnC");
        bookRepository.save(book3);

        List<String> checkIsbnList = Lists.newArrayList("isbnA", "isbnB", "isbnC", "isbnD", "isbnE");

        List<Book> findBooksByIsbnList = bookRepository.findBooksByIsbnIn(checkIsbnList);

        assertThat(findBooksByIsbnList.size()).isEqualTo(3);
        assertThat(findBooksByIsbnList.get(0).getIsbn()).isEqualTo("isbnA");
    }

    @Test
    public void saveBookSearchWithTitleSearchAndType()
    {
        // given
        String search = "name";
        BookSearchType searchType = BookSearchType.TITLE;

        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
                .search(search)
                .bookSearchType(searchType)
                .page(0)
                .size(10)
                .build();
        // when
        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        // then
        assertThat(saveBookResponses.getContent().size()).isEqualTo(10);
    }

    @Test
    public void saveBookSearchWithIsbnSearch()
    {
        String isbn = "isbn13";
        BookSearchType searchType = BookSearchType.ISBN;
        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
                .search(isbn)
                .bookSearchType(searchType)
                .page(0)
                .size(10)
                .build();
        // when
        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);

        // then
        assertThat(saveBookResponses.getContent().size()).isEqualTo(1);
        assertThat(saveBookResponses.getContent().get(0).getIsbn()).isEqualTo(isbn);
    }

    @Test
    public void saveBookSearchAllNullParamTest()
    {
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
    public void saveBookSearchByAuthor()
    {
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

    private Order givenOrder() {
        List<Book> books = givenMockWaitBooks();
        List<OrderBook> orderBookList = books.stream().map(s -> OrderBook.createOrderBook(s, 1)).collect(Collectors.toList());
        Order order = Order.createOrder(orderBookList);
        return order;
    }

    private List<Book> givenMockWaitBooks()
    {
        return Lists.newArrayList(
                Book.builder()
                    .author("아이유")
                    .name("JPA")
                    .publisher("동아출판사")
                    .isbn("123")
                    .bookStatus(BookStatus.WAIT)
                    .build(),

                Book.builder()
                        .author("이동민")
                        .name("JPA와 함께하는 JPA 프로그래밍")
                        .publisher("동아출판사")
                        .isbn("1234")
                        .bookStatus(BookStatus.WAIT)
                        .build(),

                Book.builder()
                        .author("토비")
                        .name("스프링부트")
                        .publisher("동아출판사")
                        .isbn("1235")
                        .bookStatus(BookStatus.WAIT)
                        .build()
                );
    }

    @Test
    public void testtt()
    {
        Order order2 = getOrder();
        Order order3 = getOrder2();
        orderRepository.save(order2);
        orderRepository.save(order3);
        entityManager.flush();
        entityManager.clear();


        List<Order> results =
                queryFactory
                        .selectFrom(order)
                        .leftJoin(order.orderBookList, orderBook)
                        .fetchJoin()
                        .fetch();

        List<OrdersResponse> ordersResponses = results.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());

        System.out.println(ordersResponses.get(0).getOrderId());

    }

    private Order getOrder() {
        Book newbook = new Book();
        newbook.setBookStatus(BookStatus.WAIT);
        newbook.setIsbn("126163L");
        bookRepository.save(newbook);

        Book newbook2 = new Book();
        newbook2.setBookStatus(BookStatus.WAIT);
        newbook2.setIsbn("1223223L");
        bookRepository.save(newbook2);

        OrderBook orderBook1 = OrderBook.createOrderBook(newbook, 10);
        OrderBook orderBook2 = OrderBook.createOrderBook(newbook2, 12);

        orderBookRepository.save(orderBook1);
        orderBookRepository.save(orderBook2);
        Order order2 = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2));
        return order2;
    }

    private Order getOrder2() {
        Book newbook = new Book();
        newbook.setBookStatus(BookStatus.WAIT);
        newbook.setIsbn("126162323L");
        bookRepository.save(newbook);

        Book newbook2 = new Book();
        newbook2.setBookStatus(BookStatus.WAIT);
        newbook2.setIsbn("122523223L");
        bookRepository.save(newbook2);

        OrderBook orderBook1 = OrderBook.createOrderBook(newbook, 10);
        OrderBook orderBook2 = OrderBook.createOrderBook(newbook2, 12);

        orderBookRepository.save(orderBook1);
        orderBookRepository.save(orderBook2);
        Order order2 = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2));
        return order2;
    }


}
