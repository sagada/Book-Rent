package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.exception.DuplicateBookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void setBook(@Valid BookDto.SetBookDto param)
    {
        setBookValidate(param);

        List<Book> books = param.getSetBookParamList()
                .stream()
                .map(BookDto.SetBookParam::createReadyBook)
                .collect(Collectors.toList());

        bookRepository.saveAll(books);
    }

    private void setBookValidate(BookDto.SetBookDto param) {
        List<Book> originBooks = bookRepository.findAll();

        List<String> isbns = originBooks.stream()
                .map(Book::getIsbn)
                .collect(Collectors.toList());

        Map<String, Boolean> isbnMaps = new HashMap<>();
        isbns.forEach(s-> isbnMaps.put(s, true));

        for (BookDto.SetBookParam setBook : param.getSetBookParamList())
        {
            if (isbnMaps.containsKey(setBook.getIsbn()))
            {
                throw new DuplicateBookException("등록 되어 있는 책입니다.");
            }
        }
    }

}
