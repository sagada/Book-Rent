package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<BookDto.BookInfo> getBooks(Pageable pageable, BookDto.SearchBooksParam param)
    {

        Page<Book> books =  bookRepository.getBooks(param, pageable);

        return books.stream()
                .map(book -> BookDto.BookInfo.builder()
                                .count(book.getQuantity())
                                .publisher(book.getPublisher())
                                .name(book.getName())
                                .build())
                .collect(Collectors.toList());
    }

    public void setBook(List<BookDto.SetBookParam> param)
    {
        List<Book> books = param.stream().map(BookDto.SetBookParam::toEntity).collect(Collectors.toList());
        bookRepository.saveAll(books);
    }

    public Map<String, Integer> getBookQuantity(List<String> isbns)
    {
        List<BookDto.BookQuantityResponse> responses = new ArrayList<>();

        for (String isbn : isbns)
        {
            Book book = bookRepository.findBookByIsbn(isbn).orElse(new Book().setIsbn(isbn).setQuantity(0));

            if (book.getQuantity() == 0)
            {
                responses.add(BookDto.BookQuantityResponse.createEmptyResponse(book.getIsbn()));
            }
            else
            {
                responses.add(BookDto.BookQuantityResponse.builder().isbn(book.getIsbn()).quantity(book.getQuantity())
                .build());
            }
        }

        return responses.stream()
                .collect(Collectors.toMap(
                        BookDto.BookQuantityResponse::getIsbn, BookDto.BookQuantityResponse::getQuantity
                ));
    }
}
