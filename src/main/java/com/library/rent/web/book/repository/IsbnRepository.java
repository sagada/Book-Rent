package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Isbn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IsbnRepository extends JpaRepository<Isbn, Long> {
    boolean existsByIsbnNmIn(List<String> isbns);
}
