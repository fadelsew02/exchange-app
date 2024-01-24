package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.exchangeApp.model.User;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<User, Long>{
    public Optional<User> findByEmail(String email);
}


