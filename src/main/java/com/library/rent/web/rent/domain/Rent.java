package com.library.rent.web.rent.domain;

import com.library.rent.web.member.domain.Member;
import com.library.rent.web.rentbook.domain.RentBook;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(exclude = {"member" , "bookList"})
@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime rentTime;
    private LocalDateTime returnDate;
    private boolean isRent;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "rent")
    private List<RentBook> bookList;
}
