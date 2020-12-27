package com.library.rent.web.book.service;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookRepository;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void setBook(@Valid BookDto.SetBookDto param)
    {
        setBookValidate(param);

        List<Book> books = param.getSetBookParamList()
                .stream()
                .map(BookDto.SetBookParam::createReadyBook)
                .collect(Collectors.toList());

        bookRepository.saveAll(books);
    }

    private void setBookValidate(BookDto.SetBookDto param)
    {
        List<String> inputIsbnList = param.getSetBookParamList()
                .stream()
                .map(BookDto.SetBookParam::getIsbn)
                .collect(Collectors.toList());

        List<Book> findBooksByInputIsbnList = bookRepository.findBooksByIsbnIn(inputIsbnList);

        if (!CollectionUtils.isEmpty(findBooksByInputIsbnList))
        {
            // 중복 되는 책들의 이름을 ErrorResponse 에 넘겨준다.
            String detailMessage = findBooksByInputIsbnList
                    .stream()
                    .map(Book::getName)
                    .collect(Collectors.joining(",\n"));

            throw new GlobalApiException(ErrorCode.DUPLICATE_BOOK, detailMessage);
        }
    }
}
