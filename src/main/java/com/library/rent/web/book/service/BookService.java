package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void setBook(BookDto.SetBookDto param)
    {
        param.validate();

        List<Book> books = param.getSetBookParamList()
                .stream()
                .map(BookDto.SetBookParam::createReadyBook)
                .collect(Collectors.toList());

        bookRepository.saveAll(books);
    }

}
