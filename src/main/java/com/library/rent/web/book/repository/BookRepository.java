package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findBookByIsbn(String isbn);
}
