package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.taux_change;

@Repository
public interface tauxDeChangeRepo extends JpaRepository<taux_change, Long>{
    taux_change findByDeviseSourceAndDeviseDestination(String deviseSource, String deviseDestination);
}


