package com.library.rent.web.auth;


import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
