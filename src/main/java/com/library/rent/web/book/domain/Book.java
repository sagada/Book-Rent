package com.library.rent.web.book.domain;

import com.library.rent.web.rentbook.domain.RentBook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"rentBooks"})
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String isbn;
    private int count;
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @OneToMany(mappedBy = "book")
    private List<RentBook> rentBooks;

}
