

package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.transaction;

@Repository
public interface transactionRepo extends JpaRepository<transaction, Long>{

    
}


