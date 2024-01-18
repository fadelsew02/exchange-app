package com.example.exchangeApp.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


// @Data
@Entity
@Table (name="users")

public class user {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Nonnull
	private Long user_id;
    
    @Column
	@Nonnull
	private String firstName;

    @Column
	@Nonnull
	private String surName;

    @Column(unique = true)
	@Nonnull
	private String email;

    @Column
	@Nonnull
	private Double solde;

    @Column
	@Nonnull
	private String password;

    @Column
	@Nonnull
	private String tel;


	public user() {
		
	}

	public Long getId() {
		return user_id;
	}

	public void setId(Long user_id) {
		this.user_id = user_id;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}


}







