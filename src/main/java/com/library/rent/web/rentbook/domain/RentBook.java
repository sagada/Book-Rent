package com.library.rent.web.rentbook.domain;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.rent.domain.Rent;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(exclude = {"book", "rent"})
@Entity
public class RentBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cnt;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Rent rent;
}
