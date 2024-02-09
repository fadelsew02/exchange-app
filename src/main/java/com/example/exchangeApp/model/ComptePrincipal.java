package com.example.exchangeApp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@AllArgsConstructor

@EqualsAndHashCode(callSuper=false)
// @NoArgsConstructor
@DiscriminatorValue("principal")
public class ComptePrincipal extends Compte {

    @Column(nullable = true)
    private String accountHolderName;

    @Column(nullable = true)
    private Double soldePrincipal;

    @Column(nullable = true)
    private Double overdraftLimit;

    // Constructeur de ComptePrincipal
    public ComptePrincipal() {
        super("1234-5678-9012-3456-7890"); 
    }

}
