package com.example.exchangeApp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table (name="transactions")

public class transaction {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Nonnull
	private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_source_id")  
    private user userSource;

    @ManyToOne
    @JoinColumn(name = "user_destination_id")  
    private user userDestination;

    @Column
	@Nonnull
	private Double transaction_amount;

    @Column
	@Nonnull
	private String devise_source;

    @Column
	@Nonnull
	private String devise_destination;

}








