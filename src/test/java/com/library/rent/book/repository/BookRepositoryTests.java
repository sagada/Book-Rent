package com.library.rent.book.repository;

import com.library.rent.util.page.PageRequest;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:sql/book-search-test.sql")
@Transactional
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
    public void 책_이름으로_책_찾기()
    {
        // given
        String searchBookName = "#TEST#";
        BookDto.SearchBooksParam param = BookDto.SearchBooksParam.builder()
                .name(searchBookName)
                .build();

        //when
        List<Book> bookInfos = bookRepository.getBooksAdmin(param);

        //then
        assertThat(bookInfos.size()).isEqualTo(5);
    }

    @Test
    public void 책_출판사와_이름으로_책_찾기()
    {
        // given
        String searchBookName = "#TEST#";
        String publisherName = "길벗";

        BookDto.SearchBooksParam param = BookDto.SearchBooksParam.builder()
                .name(searchBookName)
                .publisher(publisherName)
                .build();
        //when
        List<Book> bookInfos = bookRepository.getBooksAdmin(param);

        //then
        assertThat(bookInfos.size()).isEqualTo(2);
        assertThat(bookInfos.get(0).getPublisher()).isEqualTo(publisherName);
    }

    @Test
    public void 북_페이징_테스트()
    {
        // given
        String searchBookName = "길벗";
        BookDto.SearchBooksParam param = BookDto.SearchBooksParam.builder()
                .publisher(searchBookName)
                .build();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setDirection(Sort.Direction.DESC);
        pageRequest.setPage(0);
        pageRequest.setSize(30);

        Page<Book> bookInfos = bookRepository.getBooks(param, pageRequest.of());
        List<BookDto.BookInfo> result = bookInfos.stream()
                .map(book -> BookDto.BookInfo.builder()
                        .count(book.getCount())
                        .isbn(book.getIsbn())
                        .publisher(book.getPublisher())
                        .name(book.getName())
                        .build())
                .collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(30);
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
            book.setCount(20);
            book.setPublisher("바람" + i);
            bookRepository.save(book);
        }
    }
}
