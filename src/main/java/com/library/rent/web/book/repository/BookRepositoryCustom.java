package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
     List<Book> getBooksAdmin(BookDto.SearchBooksParam param);

     Page<Book> getBooks(BookDto.SearchBooksParam param, Pageable pageable);
}
