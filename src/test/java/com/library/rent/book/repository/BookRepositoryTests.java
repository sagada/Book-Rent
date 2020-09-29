package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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



}
