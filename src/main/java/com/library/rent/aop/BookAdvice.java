package com.library.rent.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.rent.web.book.domain.BookLog;
import com.library.rent.web.book.dto.BookDto;
import com.library.rent.web.book.repository.BookLogRepository;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
@Aspect
@Log4j2
public class BookAdvice {

    private final BookLogRepository bookLogRepository;
    private final ObjectMapper mapper;

    @Autowired
    public BookAdvice(BookLogRepository bookLogRepository, ObjectMapper mapper) {
        this.bookLogRepository = bookLogRepository;
        this.mapper = mapper;
    }

    @Before("execution(* com.library.rent.web.book.service.BookService.orderBook(..))")
    public void orderLog(JoinPoint jp) throws JsonProcessingException {

        log.info("advice method : {}", jp.getSignature().getName());
        log.info("orderBook Before Advice");

        // 서비스 메서드 한개의 파라미터 보장 함
        Object obj = jp.getArgs()[0];

        if (obj instanceof BookDto.SetBookDto)
        {
            BookDto.SetBookDto param = (BookDto.SetBookDto) obj;
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(param);
            bookLogRepository.save(BookLog.createLogBook(json));
        }
    }

    @AfterReturning("execution(* com.library.rent.web.order.service.OrderService.stock(..))")
    public void stockLog(JoinPoint jp) throws JsonProcessingException {

        log.info("advice method : {}", jp.getSignature().getName());
        log.info("createOrderBook AfterReturning Advice");


        Object obj = jp.getSignature().getName();

        System.out.println(obj);
    }


}
