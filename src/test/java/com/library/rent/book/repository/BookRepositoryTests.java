package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.repository.BookRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/book-search-test.sql")
@Transactional
@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

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
        book1.setIsbn("isbn1");
        bookRepository.save(book1);

        Book book2 = new Book(bookName);
        book2.setIsbn("isbn2");
        bookRepository.save(book2);

        Book book3 = new Book(bookName);
        book3.setIsbn("isbn3");
        bookRepository.save(book3);

        List<String> checkIsbnList = Lists.newArrayList("isbn1", "isbn2", "isbn3", "isbn4", "isbn5");

        List<Book> findBooksByIsbnList = bookRepository.findBooksByIsbnIn(checkIsbnList);

        assertThat(findBooksByIsbnList.size()).isEqualTo(3);
        assertThat(findBooksByIsbnList.get(0).getIsbn()).isEqualTo("isbn1");
    }
}
