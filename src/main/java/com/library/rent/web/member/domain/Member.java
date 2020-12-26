package com.library.rent.web.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    private String id;
    private String name;


    @Builder
    public Member(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
