package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> ,BookRepositoryCustom {
}
