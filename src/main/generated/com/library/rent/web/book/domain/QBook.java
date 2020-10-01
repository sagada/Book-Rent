package com.library.rent.web.book.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1603365699L;

    public static final QBook book = new QBook("book");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath isbn = createString("isbn");

    public final StringPath name = createString("name");

    public final StringPath publisher = createString("publisher");

    public final ListPath<com.library.rent.web.rentbook.domain.RentBook, com.library.rent.web.rentbook.domain.QRentBook> rentBooks = this.<com.library.rent.web.rentbook.domain.RentBook, com.library.rent.web.rentbook.domain.QRentBook>createList("rentBooks", com.library.rent.web.rentbook.domain.RentBook.class, com.library.rent.web.rentbook.domain.QRentBook.class, PathInits.DIRECT2);

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

