package com.library.rent.web.book.repository;


import com.library.rent.web.book.dto.BookSearchRequest;
import com.library.rent.web.book.dto.SaveBookResponse;
import org.springframework.data.domain.Page;


public interface BookRepositoryCustom {
    Page<SaveBookResponse> searchBookWithPaging(BookSearchRequest bookSearchRequest);
}
