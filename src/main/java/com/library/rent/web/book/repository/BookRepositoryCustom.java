package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;

import java.util.List;

public interface BookRepositoryCustom {
     List<Book> getBooks(BookDto.SearchBooksParam param);
}
