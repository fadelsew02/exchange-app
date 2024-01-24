package com.example.exchangeApp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.Validation;


@Repository
public interface ValidationRepo extends JpaRepository<Validation, Long>{
    Optional<Validation> findByCode(String code);
}


