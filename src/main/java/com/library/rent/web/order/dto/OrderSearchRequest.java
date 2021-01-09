package com.library.rent.web.order.dto;

import com.library.rent.web.order.domain.OrderStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OrderSearchRequest {
    
    @Min(value = 0, message = "아이디는 1이상입니다.")
    private Long orderId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime endDt;
    private OrderStatus orderStatus;
    @NotNull(message = "page 숫자를 입력해주세요.")
    @Min(0)
    private Integer page = 0;

    @NotNull(message = "page 사이즈를 입력해주세요.")
    @Min(0)
    private Integer size = 10;
    public void validate() {
    }
}
