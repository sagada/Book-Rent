package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {

    public BookRepositoryImpl()
    {
        super(Book.class);
    }

    @Override
    public List<BookDto.BookInfo> getBooks(BookDto.SearchBooksParam param)
    {
        return null;
    }
}
