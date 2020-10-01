package com.library.rent.web.member.domain;

import com.library.rent.web.rent.domain.Rent;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@ToString(exclude = {"rents"})
@Entity
public class Member {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Rent> rents;
}
