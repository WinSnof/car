package com.service.car.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class CardInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String cardNumber;
    private String date;
    private String cvc;
}
