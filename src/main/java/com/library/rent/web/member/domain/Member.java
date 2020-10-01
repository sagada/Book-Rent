package com.library.rent.web.member.domain;

import com.library.rent.web.rent.domain.Rent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString(exclude = {"rents"})
@Entity
public class Member {

    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Rent> rents;

    @Builder
    public Member(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
