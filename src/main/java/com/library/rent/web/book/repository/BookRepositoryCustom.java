package com.library.rent.web.book.repository;


import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookSearchRequest;
import com.library.rent.web.book.dto.SaveBookResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface BookRepositoryCustom {
    Page<SaveBookResponse> searchBookWithPaging(BookSearchRequest bookSearchRequest);
    Book searchBookByIsbnList(List<String> isbn);
}
