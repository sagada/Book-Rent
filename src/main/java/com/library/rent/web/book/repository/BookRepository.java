package com.library.rent.web.book.repository;

import com.library.rent.web.book.domain.Book;
import com.library.rent.web.book.domain.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom{
    List<Book> findBooksByIsbnIn(List<String> isbnList);

    @Modifying
    @Query("Update Book b set b.bookStatus = :bookStatus where b.id in :bookList")
    void updateBookStatus(BookStatus bookStatus, List<Long> bookList);
}
