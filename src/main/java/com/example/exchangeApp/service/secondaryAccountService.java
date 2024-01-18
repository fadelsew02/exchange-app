package com.example.exchangeApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.exchangeApp.repo.secondaryAccountRepo;

public class secondaryAccountService {

    @Autowired
    secondaryAccountRepo secondaryAccountRepo;


        public boolean compareAmountInAccount(Double amountToSend, String deviseDestination) {
            Double amountRemaining = secondaryAccountRepo.findByDeviseDestination(deviseDestination);

            if (amountRemaining <= amountToSend) {
                return false;
            } 

            Double reliquat = amountRemaining-amountToSend;
            

            return true;
        }
    
}








