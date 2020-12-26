package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

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

        System.out.println("createdDate = " + book.getCreatedDate());
        System.out.println("updatedDate = " + book.getLastModifiedDate());
    }
}
