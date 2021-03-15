package com.library.rent.web.book.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"book"})
public class ISBN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name= "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private String isbn;

    public void setBook(Book book) {
        this.book = book;
    }

    public ISBN(String isbn) {
        this.isbn = isbn;
    }
}
