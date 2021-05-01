package com.library.rent.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQL5InnoDBDialect extends MySQL5Dialect {
    public CustomMySQL5InnoDBDialect() {
        super();
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));
    }
}
