
package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.Bank;

@Repository
public interface BankRepo extends JpaRepository<Bank, Integer>{

}
