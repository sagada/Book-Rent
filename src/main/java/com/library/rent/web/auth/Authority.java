package com.library.rent.web.auth;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "authority")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority {
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}