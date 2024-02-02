
package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.ComptePrincipal;

@Repository
public interface ComptePrincipalRepo extends JpaRepository<ComptePrincipal, Long>{
    @Query(value = "SELECT c.solde_principal FROM compte c WHERE c.compte_type = :compte_type", nativeQuery = true)
    Double findSoldePrincipalByCompteType(@Param("compte_type") String compte_type);


    @Query(value = "SELECT * FROM compte c WHERE c.compte_type = :compte_type", nativeQuery = true)
    ComptePrincipal findByCompteType(@Param("compte_type") String compte_type);

    // Double findOverdraftLimitById(Long id);

}
