package com.library.rent.web.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    public void join(String email, String password)
    {

    }

}
