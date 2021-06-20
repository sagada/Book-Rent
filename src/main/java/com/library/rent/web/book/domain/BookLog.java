package com.library.rent.web.book.domain;


import com.library.rent.common.BaseEntity;
import com.library.rent.web.auth.AuthUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "book_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BookLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "reg_id")
    private String regId;

    public static BookLog createLogBook(String detail)
    {
        return new BookLog(detail, AuthUtil.getCurUserEmail());
    }

    public BookLog(String detail, String regId)
    {
        this.detail = detail;
        this.regId = regId;
    }
}
