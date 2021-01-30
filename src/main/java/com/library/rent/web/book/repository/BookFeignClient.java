package com.library.rent.web.book.repository;

import com.library.rent.config.BookConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "feign", url = "${kakao.book.search.url}", configuration = {BookConfiguration.class})
public interface BookFeignClient {

    @RequestMapping(value = "/book", produces = "application/json", headers = "Authorization= KakaoAK ${kakao.appkey}")
    String getBookFromKakaoOpenApi(
            @RequestParam(value = "size", defaultValue = "10") int size
            , @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "sort", defaultValue = "accuracy") String sort
            , @RequestParam(value = "query", required = true) String query
            , @RequestParam(value = "target", defaultValue = "title") String target);
}
