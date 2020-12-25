package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;

@Sql("classpath:sql/book-search-test.sql")
@Transactional
@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 책_추가하기()
    {
        for (int i = 1; i <= 100; i++)
        {
            Book book = new Book();
            book.setIsbn("V1188621270AB" + i);
            book.setName("Book" + i);
            book.setQuantity(20);
            book.setPublisher("바람" + i);
            bookRepository.save(book);
        }
    }
}
