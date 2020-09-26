package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.QBook;
import com.library.rent.web.book.dto.BookDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {


    @Autowired
    public BookRepositoryImpl()
    {
        super(Book.class);

    }

    @Override
    public List<BookDto.BookInfo> getBooks(BookDto.SearchBooksParam param)
    {
        if (ObjectUtils.isEmpty(param))
            return null;
        QBook b = QBook.book;
        JPQLQuery<Book> query = from(b);

        JPQLQuery<Tuple> tuple = query.select(b.isbn, b.name, b.publisher, b.count);

        tuple.where(b.name.eq(param.getName()));
        tuple.where(b.count.gt(param.getCount()));
        tuple.where(b.publisher.eq(param.getPublisher()));
        tuple.where(b.isbn.eq(param.getIsbn()));
        tuple.orderBy(b.isbn.desc());

        System.out.println(tuple.fetch());
        return null;
    }
}
