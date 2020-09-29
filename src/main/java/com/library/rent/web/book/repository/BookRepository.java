package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.QBook;
import com.library.rent.web.book.dto.BookDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> ,BookRepositoryCustom {

    public default Predicate makePredicate()
    {
        BooleanBuilder builder = new BooleanBuilder();

        QBook qBook = QBook.book;

        builder.and(qBook.name.like("이것이데이터베이스다"));
        return builder;
    }

}
