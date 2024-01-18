
package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.secondary_account;


@Repository
public interface secondaryAccountRepo extends JpaRepository<secondary_account, Long>{
    Double findByDeviseDestination(String deviseDestination);
    public secondary_account findByDevise(String devise);
}


