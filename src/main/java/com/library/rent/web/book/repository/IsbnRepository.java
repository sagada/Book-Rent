package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.ISBN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IsbnRepository extends JpaRepository<ISBN, Long> {
    boolean existsByIsbnIn(List<String> isbns);
}
