package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchangeApp.model.user;


@Repository
public interface userRepo extends JpaRepository<user, Long>{
    public user findByEmail(String email);
}


