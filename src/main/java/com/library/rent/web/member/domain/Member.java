package com.library.rent.web.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.rent.common.BaseEntity;
import com.library.rent.web.auth.Authority;
import com.library.rent.web.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, name = "email", unique = true)
    private String email;
    @JsonIgnore

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @JsonIgnore
    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    public Member(String email, String password, String nickname, boolean active) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.active = active;
    }

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    private Member(Long memberId, String email, String password, String nickname, boolean active, Set<Authority> authorities) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.active = active;
        this.authorities = authorities;
    }
}
