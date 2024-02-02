package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long>{
    @Query(value = "SELECT code FROM Currency WHERE name = :name LIMIT 1", nativeQuery = true)
    public String findCodeByName(@Param("name") String name);
}
