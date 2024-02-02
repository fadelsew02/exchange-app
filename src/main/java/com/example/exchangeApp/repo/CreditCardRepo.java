package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.CreditCard;
import com.example.exchangeApp.model.User;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Long>{
    public Double findByUser(User u);
}
