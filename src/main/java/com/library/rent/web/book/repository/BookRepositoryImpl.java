package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.QIsbn;
import com.library.rent.web.book.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;


import java.time.LocalDateTime;
import java.util.List;

import static com.library.rent.web.book.domain.QBook.book;
import static com.library.rent.web.book.domain.QIsbn.isbn;
import static com.library.rent.web.order.domain.QOrderBook.orderBook;


public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<SaveBookResponse> searchBookWithPaging(BookSearchRequest request) {

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());

        QueryResults<SaveBookResponse> results = queryFactory
                .select(
                        Projections.constructor(SaveBookResponse.class,
                                book.name.as("book_name")
                                , book.imgUrl
                                , book.publisher
                                , book.author
                                , orderBook.order.id
                                , Expressions.stringTemplate("group_concat({0})", isbn.isbnNm)
                        )
                ).from(book)
                .innerJoin(book.orderBookList, orderBook)
                .innerJoin(book.isbnList, isbn)
                .where(
                        searchEq(request.getSearch(), request.getBookSearchType()),
                        startGoe(request.getStartAt()),
                        endLoe(request.getEndAt())
                )
                .offset(getSearchSaveBookOffset(pageable))
                .groupBy(book.id)
                .limit(pageable.getPageSize())
                .orderBy(orderBook.createdDate.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Book searchBookByIsbnList(List<String> isbn) {
        return null;
    }

    private int getSearchSaveBookOffset(Pageable pageable) {
        if (pageable == null) {
            return 0;
        }
        return pageable.getPageNumber() * pageable.getPageSize();
    }

    private BooleanExpression endLoe(LocalDateTime endAt) {
        return endAt != null ? book.createdDate.loe(endAt) : null;
    }


    private BooleanExpression startGoe(LocalDateTime startAt) {
        return startAt != null ? book.createdDate.goe(startAt) : null;
    }

    private BooleanExpression searchEq(String search, BookSearchType searchType) {
        if (search == null || searchType == null)
            return null;
        switch (searchType) {
            case TITLE:
                return book.name.contains(search);
            case AUTHOR:
                return book.author.contains(search);
            case PUBLISHER:
                return book.publisher.contains(search);
//            case "ISBN":
//                return book.isbn.contains(search);
            default:
                break;
        }

        return null;
    }

    private BooleanExpression bookAuthorEq(String author) {
        return author != null ? book.author.eq(author) : null;
    }

    private BooleanExpression bookPublisherEq(String publisher) {
        return publisher != null ? book.publisher.eq(publisher) : null;
    }


    private BooleanExpression bookNameEq(String name) {
        return name != null ? book.name.contains(name) : null;
    }
}
