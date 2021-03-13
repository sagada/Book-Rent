package com.library.rent.web.member.service;

import com.library.rent.util.SecurityUtil;
import com.library.rent.web.auth.AuthUtil;
import com.library.rent.web.auth.Authority;
import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.dto.MemberDto;
import com.library.rent.web.member.dto.MemberSignUpResponseDto;
import com.library.rent.web.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberSignUpResponseDto signup(MemberDto memberDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        //빌더 패턴의 장점
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member user = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .nickname(memberDto.getNickname())
                .authorities(Collections.singleton(authority))
                .active(true)
                .build();

        Member saveMember = memberRepository.save(user);

        return MemberSignUpResponseDto.Ok(saveMember);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByEmail);
    }

    public Member findByEmail()
    {
        return memberRepository.findByEmail(AuthUtil.getCurUserEmail()).orElseThrow(()->
                new GlobalApiException(ErrorCode.NON_USER, "사용자 에러", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
