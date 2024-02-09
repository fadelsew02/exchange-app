

package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.CompteUtilisateur;

@Repository
public interface CompteUtilisateurRepo extends JpaRepository<CompteUtilisateur, Long>{

    @Query(value = " SELECT * FROM compte cu WHERE cu.id = (SELECT u.compte_utilisateur_id FROM users u WHERE u.id = :id);", nativeQuery = true)
    CompteUtilisateur findCompteUtilisateurById(@Param("id") Long id);

    // Double findOverdraftLimitById(Long id);

}
