package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.exchangeApp.model.TauxChange;
@Repository
public interface tauxDeChangeRepo extends JpaRepository<TauxChange, Long> {
    TauxChange findByDeviseSourceAndDeviseDestination(String deviseSource, String deviseDestination);
}
