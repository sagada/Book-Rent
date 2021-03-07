package com.library.rent.web.auth;

import com.library.rent.jwt.JwtFilter;
import com.library.rent.jwt.TokenProvider;
import com.library.rent.web.auth.dto.UserInfoResponseDto;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.dto.LoginDto;
import com.library.rent.web.member.dto.TokenDto;
import com.library.rent.web.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    public AuthController(
            TokenProvider tokenProvider
            , AuthenticationManagerBuilder authenticationManagerBuilder
            , MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt, loginDto.getEmail()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/log")
    public UserInfoResponseDto isLogin(Authentication authentication)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        ()->  new GlobalApiException(ErrorCode.NON_USER, "존재하지 않는 사용자")
                );
        return new UserInfoResponseDto(userDetails.getUsername(), member.getNickname());
    }

}