package com.library.rent.web.book.domain;

import com.library.rent.web.rentbook.domain.RentBook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table( indexes = {@Index(name = "isbn_idx", columnList = "isbn")},
        uniqueConstraints = {@UniqueConstraint(name = "NAME_ISBN_UQ", columnNames = {"isbn", "name"})})
@ToString(exclude = {"rentBooks"})
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="isbn")
    private String isbn;

    private int count;
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @OneToMany(mappedBy = "book")
    private List<RentBook> rentBooks;


}
