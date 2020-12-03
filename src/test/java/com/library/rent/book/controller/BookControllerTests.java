package com.library.rent.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.rent.web.book.dto.BookDto;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static List<BookDto.SetBookParam> givenSetAdminBookParam()
    {
        return Lists.newArrayList(
            BookDto.SetBookParam.builder()
                    .imgUrl("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F838090%3Ftimestamp%3D20201004143823")
                    .count(3)
                    .name("JPA")
                    .isbn("#########")
                    .publisher("테스트출판1")
                    .build(),


            BookDto.SetBookParam.builder()
                    .imgUrl("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F838090%3Ftimestamp%3D20201004143823")
                    .count(4)
                    .name("JAVA")
                    .isbn("#########")
                    .publisher("테스트출판2")
                    .build(),


            BookDto.SetBookParam.builder()
                    .imgUrl("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F838090%3Ftimestamp%3D20201004143823")
                    .count(3)
                    .name("SPRING")
                    .isbn("#########")
                    .publisher("테스트출판3")
                    .build()
        );
    }

    @Test
    @Transactional
    public void setBookTest() throws Exception
    {
        // given
        String requestUrl = "/api/book/admin";
        List<BookDto.SetBookParam> param = givenSetAdminBookParam();
        String jsonParam = new ObjectMapper().writeValueAsString(param);


        MockHttpServletRequestBuilder req = post(requestUrl)
                .content(jsonParam)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(req)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
