package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
//    List<Book> findBooksByIsbnIn(List<String> isbnList);

    @Query("select b from Book b left join b.isbnList i where i.isbnNm in (:isbns)")
    Optional<Book> findByIsbnList(@Param("isbns") List<String> isbns);

    @Query("select distinct b from Book b inner join b.isbnList i where i.isbnNm in (:isbns)")
    Book findBookByIsbnListIn(@Param("isbns") List<String> isbns);


}
