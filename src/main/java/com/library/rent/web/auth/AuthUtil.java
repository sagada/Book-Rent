package com.library.rent.web.auth;


import com.library.rent.web.member.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthUtil {

    public static String getCurUserEmail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }



}
