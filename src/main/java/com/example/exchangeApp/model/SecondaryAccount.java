package com.example.exchangeApp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table (name="SecondaryAccount")

public class SecondaryAccount {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Nonnull
	private Long id;
    
    @Column
	@Nonnull
	private Double amount;

    @Column
	@Nonnull
	private String devise;

}



