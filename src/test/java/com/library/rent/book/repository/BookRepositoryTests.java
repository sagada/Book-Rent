package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;



@Sql("classpath:sql/book-search-test.sql")
@Transactional
@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void 테스트_멤버_초기화()
    {
        Member testMember = Member.builder()
                .name("ADMIN")
                .id("ADMIN")
                .build();

        memberRepository.save(testMember);
    }

    @Test
    @Transactional
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
