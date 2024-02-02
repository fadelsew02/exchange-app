package com.example.exchangeApp.model;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
// import jakarta.persistence.DiscriminatorColumn;
// import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.Inheritance;
// import jakarta.persistence.InheritanceType;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Bank")
public class Bank {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String swiftCode;

    @OneToOne(cascade = CascadeType.ALL)
    private ComptePrincipal comptePrincipal;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CompteUtilisateur> comptes;
}




