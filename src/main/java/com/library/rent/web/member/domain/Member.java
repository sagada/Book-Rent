package com.library.rent.web.member.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@ToString
@Entity
public class Member {
    @Id
    private String id;
    private String name;
}
