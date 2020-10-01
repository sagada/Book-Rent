package com.library.rent.web.rent.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRent is a Querydsl query type for Rent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRent extends EntityPathBase<Rent> {

    private static final long serialVersionUID = -1212228259L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRent rent = new QRent("rent");

    public final ListPath<com.library.rent.web.rentbook.domain.RentBook, com.library.rent.web.rentbook.domain.QRentBook> bookList = this.<com.library.rent.web.rentbook.domain.RentBook, com.library.rent.web.rentbook.domain.QRentBook>createList("bookList", com.library.rent.web.rentbook.domain.RentBook.class, com.library.rent.web.rentbook.domain.QRentBook.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRent = createBoolean("isRent");

    public final com.library.rent.web.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> rentTime = createDateTime("rentTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> returnDate = createDateTime("returnDate", java.time.LocalDateTime.class);

    public QRent(String variable) {
        this(Rent.class, forVariable(variable), INITS);
    }

    public QRent(Path<? extends Rent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRent(PathMetadata metadata, PathInits inits) {
        this(Rent.class, metadata, inits);
    }

    public QRent(Class<? extends Rent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.library.rent.web.member.domain.QMember(forProperty("member")) : null;
    }

}

