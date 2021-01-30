package com.library.rent.web.book.dto;

import com.library.rent.web.book.domain.BookSearchType;
import com.library.rent.web.book.domain.BookStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookSearchRequest {

    private String search;

    @NotNull(message = "검색 타입을 입력해주세요.")
    private BookSearchType bookSearchType;

    @ApiModelProperty(example = "ALL", allowableValues = "ALL|WAIT|COMP|RENT")
    @NotNull(message = "책 상태를 입력해주세요")
    private BookStatus bookStatus;

    @NotNull(message = "page 숫자를 입력해주세요.")
    @Min(0)
    private Integer page = 0;

    @NotNull(message = "page 사이즈를 입력해주세요.")
    @Min(0)
    private Integer size = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endAt;

    @Builder
    public BookSearchRequest(String search,
                             BookSearchType bookSearchType,
                             BookStatus bookStatus,
                             Integer page,
                             Integer size,
                             LocalDateTime startAt,
                             LocalDateTime endAt) {
        this.search = search;
        this.bookSearchType = bookSearchType;
        this.bookStatus = bookStatus;
        this.page = page;
        this.size = size;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
