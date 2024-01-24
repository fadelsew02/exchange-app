package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.SecondaryAccount;

@Repository
public interface secondaryAccountRepo extends JpaRepository<SecondaryAccount, Long>{
    public Double findAmountByDevise(String devise);
    public SecondaryAccount findByDevise(String devise);
}


