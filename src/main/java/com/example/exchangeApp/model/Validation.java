package com.example.exchangeApp.model;

import java.time.Instant;

import jakarta.persistence.CascadeType;
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
@Table(name = "validation")

public class Validation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Instant creation;

    @Column
    private Instant expiration;

    @Column
    private Instant activation;

    @Column
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    private User utilisateur;
}



