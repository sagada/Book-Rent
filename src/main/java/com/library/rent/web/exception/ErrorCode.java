package com.library.rent.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NON_ROLES("권한이 존재하지 않습니다."),
    NON_USER("존재하지 않는 사용자 입니다."),
    DUPLICATE_BOOK("중복된 값 또는 데이터가 있습니다."),
    PARAMETER_ERROR("파라미터가 적절하지 않습니다."),
    NOT_FOUND_RESOURCE("찾을 수 없는 값이 있습니다."),
    LOGIC_ERROR("로직 오류가 발생 했습니다.");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
