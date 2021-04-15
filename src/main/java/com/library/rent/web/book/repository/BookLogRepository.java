package com.library.rent.web.book.repository;


import com.library.rent.web.book.domain.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLogRepository extends JpaRepository<BookLog, Long> {
}
