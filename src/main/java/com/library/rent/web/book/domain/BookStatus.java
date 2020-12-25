package com.library.rent.web.book.domain;

import lombok.Getter;

@Getter
public enum BookStatus {
    WAIT("WAIT", "입고대기"),
    READY("READY", "대여가능"),
    RENT("RENT", "대여중");

    private String name;
    private String explain;

    BookStatus(String name, String value) {
        this.name = name;
        this.explain = value;
    }
}
