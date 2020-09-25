package com.library.rent.web.rent.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRent is a Querydsl query type for Rent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRent extends EntityPathBase<Rent> {

    private static final long serialVersionUID = -1212228259L;

    public static final QRent rent = new QRent("rent");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRent = createBoolean("isRent");

    public final DateTimePath<java.time.LocalDateTime> rentTime = createDateTime("rentTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> returnDate = createDateTime("returnDate", java.time.LocalDateTime.class);

    public QRent(String variable) {
        super(Rent.class, forVariable(variable));
    }

    public QRent(Path<? extends Rent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRent(PathMetadata metadata) {
        super(Rent.class, metadata);
    }

}

