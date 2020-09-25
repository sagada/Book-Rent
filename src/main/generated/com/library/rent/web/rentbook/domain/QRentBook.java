package com.library.rent.web.rentbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRentBook is a Querydsl query type for RentBook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRentBook extends EntityPathBase<RentBook> {

    private static final long serialVersionUID = 1384291023L;

    public static final QRentBook rentBook = new QRentBook("rentBook");

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QRentBook(String variable) {
        super(RentBook.class, forVariable(variable));
    }

    public QRentBook(Path<? extends RentBook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRentBook(PathMetadata metadata) {
        super(RentBook.class, metadata);
    }

}

