package com.example.exchangeApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exchangeApp.dto.CreditCardDTO;
import com.example.exchangeApp.dto.SoldOperationDTO;
import com.example.exchangeApp.dto.TransferRequestDTO;
import com.example.exchangeApp.model.CompteUtilisateur;
import com.example.exchangeApp.model.CreditCard;
import com.example.exchangeApp.model.Transaction;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.CompteUtilisateurRepo;
import com.example.exchangeApp.repo.CreditCardRepo;
import com.example.exchangeApp.repo.userRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class CreditCardService {
    
    @Autowired
	userRepo repo;
    @Autowired
	CreditCardRepo creditCardRepo;
    @Autowired
	CompteUtilisateurRepo compteUtilisateurRepo;
    
    public boolean AddCreditCard(CreditCardDTO creditCardDTO, HttpSession session){
        User userRetrieving = (User) session.getAttribute("userConnected");

        Optional<User> userOptional = repo.findByEmail(userRetrieving.getEmail());
 
        if (userOptional.isPresent()) {
            User u = userOptional.get();

                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(creditCardDTO.cardNumber());
                creditCard.setCheckingCode(creditCardDTO.checkingCode());
                creditCard.setExpirationDate(creditCardDTO.expirationDate());
                creditCard.setFacturationAdress(creditCardDTO.facturationAdress());
                creditCard.setUser(u);
    
                creditCardRepo.save(creditCard);
                return true;
          
        } 
        return false;
    }	


    public boolean performSoldOperation(SoldOperationDTO soldOperationDTO, HttpSession session){
 
        try {
            User userRetrieving = (User) session.getAttribute("userConnected");
            // Vérifier l'opération (recharge ou retrait)
            
            if ("recharge".equals(soldOperationDTO.typeOperation())) {
                
                 CreditCard creditCard = creditCardRepo.findByUserId(userRetrieving.getId());
                 System.out.println(creditCard);
                 if (creditCard != null && creditCard.getSoldeCard() > soldOperationDTO.amount() ) {
                     creditCard.setSoldeCard(creditCard.getSoldeCard() - soldOperationDTO.amount());
                     creditCardRepo.save(creditCard);
                     System.out.println(creditCard);

                     // Mettre à jour le solde du compte utilisateur
                    CompteUtilisateur compteUtilisateur = compteUtilisateurRepo.findCompteUtilisateurById(userRetrieving.getId());
                    compteUtilisateur.setSoldeUtilisateur(compteUtilisateur.getSoldeUtilisateur() + soldOperationDTO.amount());
                    compteUtilisateurRepo.save(compteUtilisateur);
                    System.out.println(compteUtilisateur);
                     return true;
                 } else {
                     // La carte de crédit n'est pas trouvée
                     return false;
                 }

            } else if ("retrait".equals(soldOperationDTO.typeOperation())) {
                // Logique pour retirer le solde de la carte de crédit
                System.out.println(compteUtilisateurRepo.findCompteUtilisateurById(userRetrieving.getId()));
                CompteUtilisateur compteUtilisateur = compteUtilisateurRepo.findCompteUtilisateurById(userRetrieving.getId());
                System.out.println("salut t le monde");
                if (compteUtilisateur != null && compteUtilisateur.getSoldeUtilisateur() >= soldOperationDTO.amount()) {
                    compteUtilisateur.setSoldeUtilisateur(compteUtilisateur.getSoldeUtilisateur() - soldOperationDTO.amount());
                    compteUtilisateurRepo.save(compteUtilisateur);

                    // Mettre à jour le solde de la carte de crédit
                    CreditCard creditCard = creditCardRepo.findByUserId(userRetrieving.getId());
                    creditCard.setSoldeCard(creditCard.getSoldeCard() + soldOperationDTO.amount());
                    creditCardRepo.save(creditCard);
                    System.out.println(creditCard);

                    return true;
                } else {
                           // La carte de crédit n'est pas trouvée ou le solde est insuffisant
                           return false;
                }

            } else {
                // Type d'opération non pris en charge
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();  // Gérez les exceptions de manière appropriée
            return false;
        }
        
    }	
}
