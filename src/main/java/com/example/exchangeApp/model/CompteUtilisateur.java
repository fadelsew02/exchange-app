package com.example.exchangeApp.model;

import jakarta.persistence.DiscriminatorValue;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter

@DiscriminatorValue("utilisateur")
public class CompteUtilisateur extends Compte {
    @Column(nullable = true)
    private Double soldeUtilisateur;

    public static String generateRandomAccountNumber() {
        Random rand = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                cardNumber.append(rand.nextInt(10));
            }
            cardNumber.append("-");
        }
        cardNumber.deleteCharAt(cardNumber.length() - 1);

        return cardNumber.toString();
    }

    // Constructeur de ComptePrincipal
    public CompteUtilisateur() {
        super(generateRandomAccountNumber()); 
    }

}