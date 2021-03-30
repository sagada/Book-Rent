package com.library.rent.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.Isbn;
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
    void createOneBookTest()
    {
        String bookName = "JPA";
        Book book = new Book(bookName);
        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isEqualTo(book);
        assertThat(savedBook.getName()).isEqualTo(bookName);
    }

    @Test
    void bookIsbnTest()
    {
        String bookName = "JPA";
        Book book1 = new Book(bookName);
        Isbn isbn1 = new Isbn("isbn1");
        isbn1.setBook(book1);
        book1.getIsbns().add(isbn1);

        Book newBook = bookRepository.save(book1);
        assertThat(newBook.getIsbns().size()).isEqualTo(1);
        assertThat(newBook.getIsbns().get(0).getIsbn()).isEqualTo("isbn1");
    }

//
//    @Test
//    public void saveBookSearchWithTitleSearchAndType() {
//        // given
//        String search = "name";
//        BookSearchType searchType = BookSearchType.TITLE;
//
//        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
//                .search(search)
//                .bookSearchType(searchType)
//                .page(0)
//                .size(10)
//                .build();
//        // when
//        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);
//
//        // then
//        assertThat(saveBookResponses.getContent().size()).isEqualTo(10);
//    }
////
//    @Test
//    public void saveBookSearchWithIsbnSearch() {
//        String isbn = "isbn13";
//        BookSearchType searchType = BookSearchType.ISBN;
//        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
//                .search(isbn)
//                .bookSearchType(searchType)
//                .page(0)
//                .size(10)
//                .build();
//        // when
//        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);
//
//        // then
//        assertThat(saveBookResponses.getContent().size()).isEqualTo(1);
//    }
//
//    @Test
//    public void saveBookSearchAllNullParamTest() {
//        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
//                .page(0)
//                .size(10)
//                .build();
//        // when
//        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);
//
//        // then
//        assertThat(saveBookResponses.getContent().size()).isEqualTo(10);
//    }
//
//    @Test
//    public void saveBookSearchByAuthor() {
//        // given
//        String author = "베놈";
//        Book book = Book.builder()
//                .author(author)
//                .name("톰하디")
//                .build();
//
//        entityManager.persist(book);
//        BookSearchRequest bookSearchRequest = BookSearchRequest.builder()
//                .search(author)
//                .bookSearchType(BookSearchType.AUTHOR)
//                .page(0)
//                .size(10)
//                .build();
//
//        // when
//        Page<SaveBookResponse> saveBookResponses = bookRepository.searchBookWithPaging(bookSearchRequest);
//
//        // then
//        assertThat(saveBookResponses.getContent().size()).isEqualTo(1);
//        assertThat(saveBookResponses.getContent().get(0).getAuthor()).isEqualTo(author);
//    }
//
//    private Order givenOrder() {
//        Member m = new Member("awewe@naver.com", "123123", "nick11", true);
//        List<Book> books = givenMockWaitBooks();
//        List<OrderBook> orderBookList = books.stream().map(s -> OrderBook.createOrderBook(s, 1)).collect(Collectors.toList());
//        Order order = Order.createOrder(orderBookList,m);
//        return order;
//    }
//
//    private List<Book> givenMockWaitBooks() {
//        return Lists.newArrayList(
//                Book.builder()
//                        .author("아이유")
//                        .name("JPA")
//                        .publisher("동아출판사")
//                        .isbn("123")
//                        .bookStatus(BookStatus.WAIT)
//                        .build(),
//
//                Book.builder()
//                        .author("이동민")
//                        .name("JPA와 함께하는 JPA 프로그래밍")
//                        .publisher("동아출판사")
//                        .isbn("1234")
//                        .bookStatus(BookStatus.WAIT)
//                        .build(),
//
//                Book.builder()
//                        .author("토비")
//                        .name("스프링부트")
//                        .publisher("동아출판사")
//                        .isbn("1235")
//                        .bookStatus(BookStatus.WAIT)
//                        .build()
//        );
//    }
//
//    @Test
//    public void testtt() {
//        Order order2 = getOrder();
//        Order order3 = getOrder2();
//        orderRepository.save(order2);
//        orderRepository.save(order3);
//        entityManager.flush();
//        entityManager.clear();
//
//
//        List<Order> results =
//                queryFactory
//                        .selectFrom(order)
//                        .leftJoin(order.orderBookList, orderBook)
//                        .fetchJoin()
//                        .fetch();
//
//        List<OrdersResponse> ordersResponses = results.stream()
//                .map(OrdersResponse::new)
//                .collect(Collectors.toList());
//
//        System.out.println(ordersResponses.get(0).getOrderId());
//
//    }
//
//    private Order getOrder() {
//        Book newbook = new Book();
//        newbook.setBookStatus(BookStatus.WAIT);
//        newbook.setIsbn("126163L");
//        bookRepository.save(newbook);
//
//        Book newbook2 = new Book();
//        newbook2.setBookStatus(BookStatus.WAIT);
//        newbook2.setIsbn("1223223L");
//        bookRepository.save(newbook2);
//
//        OrderBook orderBook1 = OrderBook.createOrderBook(newbook, 10);
//        OrderBook orderBook2 = OrderBook.createOrderBook(newbook2, 12);
//
//        orderBookRepository.save(orderBook1);
//        orderBookRepository.save(orderBook2);
//        Member m = new Member("awewe@naver.com", "123123", "nick11", true);
//        Order order2 = Order.createOrder(Lists.newArrayList(orderBook1, orderBook2), m);
//        return order2;
//    }
}
