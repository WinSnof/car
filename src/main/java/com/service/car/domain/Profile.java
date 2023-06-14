package com.service.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "Profile")
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private String passport;
    private String phoneNumber;
    @JsonIgnore
    @OneToOne(mappedBy = "profile")
    private Account account;
    @JsonIgnore
    @ElementCollection
    private List<CardInfo> cards = new ArrayList<>();

    public Profile(String fullName, String passport, String phoneNumber, Account account) {
        this.fullName = fullName;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }
}
