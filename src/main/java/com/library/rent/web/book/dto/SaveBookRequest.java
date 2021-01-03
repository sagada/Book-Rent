package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.BookStatus;
import lombok.Data;

@Data
public class SaveBookRequest {
    String search;
    String searchType;
    BookStatus bookStatus;
}
