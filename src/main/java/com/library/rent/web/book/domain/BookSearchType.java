package com.library.rent.web.book.domain;

import lombok.Getter;

@Getter
public enum BookSearchType {
    TITLE("TITLE"),
    AUTHOR("AUTHOR"),
    PUBLISHER("PUBLISHER"),
    ISBN("ISBN");

    private String nm;

    BookSearchType(String nm) {
        this.nm = nm;
    }
}
