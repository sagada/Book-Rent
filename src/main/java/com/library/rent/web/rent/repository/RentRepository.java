package com.library.rent.web.rent.repository;

import com.library.rent.web.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
