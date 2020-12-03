package com.library.rent.web.users.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 68770035L;

    public static final QUsers users = new QUsers("users");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> loginCount = createNumber("loginCount", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath passwd = createString("passwd");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

