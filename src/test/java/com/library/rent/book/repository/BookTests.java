package com.library.rent.book.repository;

import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepositoryCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTests {

    @Autowired
    BookRepositoryCustom bookRepositoryCustom;

    @Test
    public void bookSearchTest()
    {
        BookDto.SearchBooksParam param =BookDto.SearchBooksParam.builder()
                .count(10)
                .isbn(30L)
                .name("책1")
                .publisher("동아출판사")
                .build();



    }
}
