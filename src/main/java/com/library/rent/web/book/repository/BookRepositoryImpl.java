package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.QBook;
import com.library.rent.web.book.dto.BookDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.library.rent.web.book.domain.QBook.book;


public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {


    @Autowired
    public BookRepositoryImpl()
    {
        super(Book.class);
    }

    @Override
    public List<Book> getBooksAdmin(BookDto.SearchBooksParam param)
    {
        if (ObjectUtils.isEmpty(param))
            return null;
        QBook b = QBook.book;
        JPQLQuery<Book> query = from(b);

        return query.from(b)
                .where(conTainsName(param.getName()),
                       conTainsIsbn(param.getIsbn()),
                       conTainsPublisher(param.getPublisher()))
                .fetch();
    }

    @Override
    public Page<Book> getBooks(BookDto.SearchBooksParam param, Pageable pageable) {

        if (ObjectUtils.isEmpty(param))
            return null;
        QBook b = QBook.book;
        JPQLQuery<Book> query = from(b);

        QueryResults<Book> results = query.from(b)
                .where(conTainsName(param.getName()),
                        conTainsIsbn(param.getIsbn()),
                        conTainsPublisher(param.getPublisher()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    private BooleanExpression conTainsName(String name)
    {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return book.name.contains(name);
    }

    private BooleanExpression conTainsIsbn(String isbn)
    {
        if (StringUtils.isEmpty(isbn)) {
            return null;
        }
        return book.isbn.contains(isbn);
    }

    private BooleanExpression conTainsPublisher(String publisher)
    {
        if (StringUtils.isEmpty(publisher)) {
            return null;
        }
        return book.publisher.contains(publisher);
    }
}
