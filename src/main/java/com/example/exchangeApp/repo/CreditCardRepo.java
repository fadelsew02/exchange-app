package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.exchangeApp.model.CreditCard;
// import com.example.exchangeApp.model.User;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Long>{
    
    @Query(value = "SELECT * FROM credit_card c WHERE c.user_id = :id;", nativeQuery = true)
    public CreditCard findByUserId(@Param("id") Long id);
    
}
