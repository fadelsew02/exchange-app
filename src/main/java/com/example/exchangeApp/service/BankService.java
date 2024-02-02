package com.example.exchangeApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exchangeApp.model.Bank;
import com.example.exchangeApp.model.ComptePrincipal;
import com.example.exchangeApp.repo.BankRepo;
import com.example.exchangeApp.repo.ComptePrincipalRepo;

@Service
public class BankService {
    @Autowired
    BankRepo repo;
    @Autowired
    ComptePrincipalRepo comptePrincipalRepo;
    
    public boolean SaveBank(){
        try {
            ComptePrincipal comptePrincipal = new ComptePrincipal();
            comptePrincipal.setId(1L);
            comptePrincipal.setAccountHolderName("eTransfer");
            comptePrincipal.setOverdraftLimit(5000.0);
            comptePrincipal.setSoldePrincipal(50000.0);
            Bank bank = new Bank();
            bank.setId(1);
            bank.setBankName("eTransferBank");
            bank.setComptePrincipal(comptePrincipal);
            bank.setSwiftCode("ABCDUS33XXX");
            // comptePrincipalRepo.save(comptePrincipal);
            
            repo.save(bank);
            return true;
            
        } catch (Exception e) {
            return false;
            // Gérer l'exception selon les besoins
        }
        
    }
}
