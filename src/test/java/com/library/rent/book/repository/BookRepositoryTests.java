package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.dto.SaveBookResponse;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.order.Order;
import com.library.rent.web.order.OrderBook;
import com.library.rent.web.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

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

    @BeforeEach
    public void init()
    {
        for (int i = 1; i <= 30; i++)
        {
            Book book = Book.builder()
                    .bookStatus(BookStatus.WAIT)
                    .author("author" + i)
                    .imgUrl("imgUrl" + i)
                    .isbn("isbn" + i)
                    .name("name" +i)
                    .publisher("publisher" + i)
                    .build();
            entityManager.persist(book);
            entityManager.flush();
            entityManager.clear();
        }
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
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        List<SaveBookResponse> saveBookResponses = bookRepository.searchSaveBook(search, searchType, null, pageRequest);

        // then
        assertThat(saveBookResponses.size()).isEqualTo(10);
    }

    @Test
    public void saveBookSearchWithIsbnSearch()
    {
        String isbn = "isbn13";
        BookSearchType searchType = BookSearchType.ISBN;
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        List<SaveBookResponse> saveBookResponses = bookRepository.searchSaveBook(isbn, searchType, null, pageRequest);

        // then
        assertThat(saveBookResponses.size()).isEqualTo(1);
        assertThat(saveBookResponses.get(0).getIsbn()).isEqualTo(isbn);
    }

    @Test
    public void saveBookSearchAllNullParamTest()
    {
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        List<SaveBookResponse> saveBookResponses = bookRepository.searchSaveBook(null, null, null, pageRequest);

        // then
        assertThat(saveBookResponses.size()).isEqualTo(10);
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


        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        List<SaveBookResponse> saveBookResponses = bookRepository.searchSaveBook(author, BookSearchType.AUTHOR, null, pageRequest);

        // then
        assertThat(saveBookResponses.size()).isEqualTo(1);
        assertThat(saveBookResponses.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void saveWaitBookSearchParameter()
    {
        // given
        List<Book> mockBooks = givenMockWaitBooks();
        bookRepository.saveAll(mockBooks);
        PageRequest pageRequest = PageRequest.of(0, 10);
        String search = "JPA";
        // when
        List<SaveBookResponse> saveBookResponses = bookRepository.searchSaveBook(search, BookSearchType.TITLE, null, pageRequest);

        // then
        assertThat(saveBookResponses.size()).isEqualTo(2);
        assertThat(saveBookResponses).extracting("name").contains("JPA", "JPA와 함께하는 JPA 프로그래밍");
    }

    @Test
    public void searchBooksWithPaging()
    {
        Order order = givenOrder();
        orderRepository.save(order);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<SaveBookResponse> result = bookRepository.("JPA", BookSearchType.TITLE, null, pageRequest);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("JPA");
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


}
