package com.library.rent.web.member.controller;

import com.library.rent.web.auth.AuthUtil;
import com.library.rent.web.member.domain.Member;
import com.library.rent.web.member.dto.MemberDto;
import com.library.rent.web.member.dto.MemberSignUpResponseDto;
import com.library.rent.web.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService userService;

    public MemberController(MemberService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberSignUpResponseDto> signup(
            @Valid @RequestBody MemberDto memberDto
    )
    {
        return ResponseEntity.ok(userService.signup(memberDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

    @GetMapping("/user/check")
    public String test(){
        return AuthUtil.getCurUserEmail();
    }
}
