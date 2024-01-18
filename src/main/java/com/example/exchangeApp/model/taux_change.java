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
@Table (name="taux_change")

public class taux_change {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Nonnull
	private Long id;
    
    @Column
	@Nonnull
	private String devise_source;

    @Column
	@Nonnull
	private String devise_destination;

    @Column
	@Nonnull
	private Double taux;
}
