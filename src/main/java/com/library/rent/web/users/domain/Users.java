package com.library.rent.web.users.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(
        name = "unq_user_email",
        columnNames = {"email"}
)})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(80)", nullable = false)
    private String passwd;

    @Column(columnDefinition = "varchar(255) DEFAULT NULL")
    private String profileImageUrl;

    @Column(columnDefinition = "int DEFAULT 0", nullable = false)
    private Integer loginCount;

    @Column(columnDefinition = "datetime DEFAULT NULL")
    private LocalDateTime lastLoginAt;

    @Column(columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()")
    private LocalDateTime createdAt;
}
