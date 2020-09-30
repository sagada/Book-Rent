package com.library.rent.book.repository;

import com.library.rent.util.page.PageRequest;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:sql/book-search-test.sql")
@Transactional
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책_이름으로_책_찾기() throws Exception
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
    public void 책_출판사와_이름으로_책_찾기() throws Exception
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
        assertThat(bookInfos.size()).isEqualTo(1);
        assertThat(bookInfos.get(0).getPublisher()).isEqualTo(publisherName);
    }

    @Test
    public void 북_페이징_테스트() throws Exception
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

        for (Book book : bookInfos)
        {
            System.out.println(book);
        }
    }

    @Test
    @Commit
    public void 책_초기화() throws Exception
    {
        for (int i = 1 ; i <= 100; i++)
        {
            Book book = new Book();
            book.setPublisher("동아출판");
            book.setName("이것이데이터베이스다"+i);
            book.setCount(110 - i);
            book.setIsbn("ABC0123DEF");
            bookRepository.save(book);
        }
    }

}
