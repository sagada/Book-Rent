package com.library.rent.web.book.service;

import com.library.rent.util.page.PageRequest;
import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void setBooksXlsx(BookDto.SetBooksXlsxParam param)
    {

    }

    public List<BookDto.BookInfo> getBooksAdmin(BookDto.SearchBooksParam param)
    {
        return null;
    }

    public List<BookDto.BookInfo> getBooks(Pageable pageable, BookDto.SearchBooksParam param) {
        Page<Book> books =  bookRepository.getBooks(param, pageable);
        return books.stream()
                .map(book -> BookDto.BookInfo.builder()
                                .count(book.getCount())
                                .isbn(book.getIsbn())
                                .publisher(book.getPublisher())
                                .name(book.getName())
                                .build())
                .collect(Collectors.toList());
    }

}
