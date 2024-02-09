package com.example.exchangeApp.model;

import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity
@Table (name="CreditCard")


public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String cardNumber;

    @Column(nullable = true)
    private String expirationDate;

    @Column(nullable = true)
    private String checkingCode;

    @Column(nullable = true)
    private String facturationAdress;

    @Column(nullable = true)
    private Double soldeCard;

    @OneToOne
    private User user;

    public CreditCard() {
        this.soldeCard = generateRandomSolde();
    }

    private Double generateRandomSolde() {
        Random random = new Random();
        return 10000 + (100000 - 10000) * random.nextDouble();
    }
}
