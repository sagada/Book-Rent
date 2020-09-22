package com.library.rent.web.rentbook.repository;

import com.library.rent.web.rentbook.domain.RentBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentBookRepository extends JpaRepository<RentBook, Long> {
}
