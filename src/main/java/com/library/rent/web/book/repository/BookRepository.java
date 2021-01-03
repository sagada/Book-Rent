package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom{
    List<Book> findBooksByIsbnIn(List<String> isbnList);
}
