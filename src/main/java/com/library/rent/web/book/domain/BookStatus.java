package com.library.rent.web.book.domain;

import lombok.Getter;

@Getter
public enum BookStatus {
    ALL("ALL", "전체"),
    WAIT("WAIT", "입고대기"),
    COMP("COMP", "렌탈가능"),
    CANCEL("CANCEL", "입고 취소"),
    RENT("RENT", "렌탈중");

    private String name;
    private String explain;

    BookStatus(String name, String value) {
        this.name = name;
        this.explain = value;
    }
}
