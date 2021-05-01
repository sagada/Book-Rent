package com.library.rent.web.book.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"isbnNm", "id"})
@Table(name ="isbn")
public class Isbn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn_id")
    private Long id;

    @JoinColumn(name= "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column(name = "isbn_nm")
    private String isbnNm;

    public void setBook(Book book)
    {
        this.book = book;
    }

    public static Isbn newIsbn(String isbn)
    {
        return new Isbn(isbn);
    }

    public Isbn(String isbnNm)
    {
        this.isbnNm = isbnNm;
    }
}
