package com.library.rent.web.rentbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentBook is a Querydsl query type for RentBook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRentBook extends EntityPathBase<RentBook> {

    private static final long serialVersionUID = 1384291023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentBook rentBook = new QRentBook("rentBook");

    public final com.library.rent.web.book.domain.QBook book;

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.library.rent.web.rent.domain.QRent rent;

    public QRentBook(String variable) {
        this(RentBook.class, forVariable(variable), INITS);
    }

    public QRentBook(Path<? extends RentBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentBook(PathMetadata metadata, PathInits inits) {
        this(RentBook.class, metadata, inits);
    }

    public QRentBook(Class<? extends RentBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.library.rent.web.book.domain.QBook(forProperty("book")) : null;
        this.rent = inits.isInitialized("rent") ? new com.library.rent.web.rent.domain.QRent(forProperty("rent"), inits.get("rent")) : null;
    }

}

