package com.library.rent.web.book.domain;

import com.library.rent.web.rentbook.domain.RentBook;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table( indexes = {
            @Index(name = "isbn_idx", columnList = "isbn"),
            @Index(name = "name_idx", columnList = "name")
        },
        uniqueConstraints = {
            @UniqueConstraint(name = "NAME_ISBN_UQ", columnNames = {"isbn", "name"})
        })
@ToString(exclude = {"rentBooks"})
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="isbn")
    private String isbn;

    private int quantity;
    private String publisher;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @OneToMany(mappedBy = "book")
    private List<RentBook> rentBooks;

    @Builder
    public Book(String name, String isbn, int quantity, String publisher, String author, String imgUrl) {
        this.name = name;
        this.isbn = isbn;
        this.quantity = quantity;
        this.publisher = publisher;
        this.author = author;
        this.imgUrl = imgUrl;
    }
}
