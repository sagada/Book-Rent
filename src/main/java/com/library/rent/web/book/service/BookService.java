package com.library.rent.web.book.service;

import com.library.rent.web.book.dto.BookDto;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    public void setBooksXlsx(BookDto.SetBooksXlsxParam param)
    {

    }

    public List<BookDto.BookInfo> getBooks(BookDto.SearchBooksParam param)
    {
        System.out.println(param.getIsbn());
        return null;
    }
}
