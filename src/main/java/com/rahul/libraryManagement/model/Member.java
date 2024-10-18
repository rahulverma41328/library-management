package com.rahul.libraryManagement.model;


import com.rahul.libraryManagement.constants.MemberShipStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate membershipDate;

    @Enumerated(EnumType.STRING)
    private MemberShipStatus membershipStatus;

}


