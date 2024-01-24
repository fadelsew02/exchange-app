package com.example.exchangeApp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.exchangeApp.TypeDeRole;

@NoArgsConstructor
@Data
@Entity
@Table (name="Role")

public class Role {
    @Id
	@Column
	@Nonnull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "VARCHAR(255)")
	@Enumerated(EnumType.STRING)
	private TypeDeRole libelle;
   
}
