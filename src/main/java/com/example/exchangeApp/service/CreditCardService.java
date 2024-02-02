package com.example.exchangeApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exchangeApp.dto.CreditCardDTO;
import com.example.exchangeApp.dto.TransferRequestDTO;
import com.example.exchangeApp.model.CreditCard;
import com.example.exchangeApp.model.Transaction;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.CreditCardRepo;
import com.example.exchangeApp.repo.userRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class CreditCardService {
    
    @Autowired
	userRepo repo;
    @Autowired
	CreditCardRepo creditCardRepo;
    
    public boolean AddCreditCard(CreditCardDTO creditCardDTO, HttpSession session){
        User userRetrieving = (User) session.getAttribute("userConnected");

        Optional<User> userOptional = repo.findByEmail(userRetrieving.getEmail());
 
        if (userOptional.isPresent()) {
            User u = userOptional.get();

                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(creditCardDTO.cardNumber());
                creditCard.setCardtype(creditCardDTO.cardType());
                creditCard.setCheckingCode(creditCardDTO.checkingCode());
                creditCard.setExpirationDate(creditCardDTO.expirationDate());
                creditCard.setFacturationAdress(creditCardDTO.facturationAdress());
                creditCard.setUser(u);
    
                creditCardRepo.save(creditCard);
                return true;
          
        } 
        return false;
    }	
}
