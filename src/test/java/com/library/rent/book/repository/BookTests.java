package com.library.rent.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.book.repository.BookRepositoryCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void bookSearchTest()
    {
        BookDto.SearchBooksParam param = new BookDto.SearchBooksParam();
        param.setName("이것이데이");
        List<Book> bookInfos = bookRepository.getBooks(param);
        System.out.println("SIZE "+ bookInfos.size());
        bookInfos.forEach(s-> System.out.println(s.getName()));
    }

    @Test
    public void initBook()
    {

        for (int i  =0 ; i < 10 ; i++)
        {
            Book book = new Book();

            book.setIsbn("1A");
            book.setName("이것이데이터베이스다 "+ i);
            book.setPublisher("동아출판사");
            bookRepository.save(book);
        }
    }
}
