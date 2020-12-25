package com.library.rent.web.book.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1603365699L;

    public static final QBook book = new QBook("book");

    public final StringPath author = createString("author");

    public final EnumPath<BookStatus> bookStatus = createEnum("bookStatus", BookStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath isbn = createString("isbn");

    public final StringPath name = createString("name");

    public final StringPath publisher = createString("publisher");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

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

