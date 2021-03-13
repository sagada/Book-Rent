package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.BookStatus;
import com.library.rent.web.book.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;


import java.time.LocalDateTime;

import static com.library.rent.web.book.domain.QBook.book;
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
                                book.name.as("book_name"), book.isbn, book.imgUrl, book.publisher, book.author, book.bookStatus, orderBook.order.id)
                ).from(book)
                .innerJoin(book.orderBookList, orderBook)
                .where(
                        searchEq(request.getSearch(), request.getBookSearchType()),
                        statusEq(request.getBookStatus()),
                        startGoe(request.getStartAt()),
                        endLoe(request.getEndAt())
                )
                .offset(getSearchSaveBookOffset(pageable))
                .limit(pageable.getPageSize())
                .orderBy(orderBook.createdDate.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
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

    private BooleanExpression statusEq(BookStatus bookStatus) {
        if (bookStatus == BookStatus.ALL)
            return null;

        return bookStatus != null ? book.bookStatus.eq(bookStatus) : null;
    }

    private BooleanExpression searchEq(String search, BookSearchType searchType) {
        if (search == null || searchType == null)
            return null;
        switch (searchType.getNm()) {
            case "TITLE":
                return book.name.contains(search);
            case "AUTHOR":
                return book.author.contains(search);
            case "PUBLISHER":
                return book.publisher.contains(search);
            case "ISBN":
                return book.isbn.contains(search);
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

    private BooleanExpression bookIsbnEq(String isbn) {
        return isbn != null ? book.isbn.eq(isbn) : null;
    }

    private BooleanExpression bookNameEq(String name) {
        return name != null ? book.name.contains(name) : null;
    }
}
