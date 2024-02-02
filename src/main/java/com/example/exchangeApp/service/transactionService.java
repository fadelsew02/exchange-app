package com.example.exchangeApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import com.example.exchangeApp.dto.TransferRequestDTO;
import com.example.exchangeApp.dto.TransferRequestDeviseDTO;
import com.example.exchangeApp.model.ApiKey;
import com.example.exchangeApp.model.ComptePrincipal;
import com.example.exchangeApp.model.Transaction;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.ComptePrincipalRepo;
import com.example.exchangeApp.repo.CreditCardRepo;
import com.example.exchangeApp.repo.CurrencyRepo;
import com.example.exchangeApp.repo.transactionRepo;
import com.example.exchangeApp.repo.userRepo;

import jakarta.servlet.http.HttpSession;

@Service

public class transactionService {

    @Autowired
	userRepo repo;
    @Autowired
	CurrencyRepo currencyRepo;
    @Autowired
	ComptePrincipalRepo comptePrincipalRepo;
    @Autowired
	transactionRepo transactionRepo;
    @Autowired
	CreditCardRepo creditCardRepo;

    public boolean transferMoney(TransferRequestDTO transferRequestDTO, HttpSession session){
        User userRetrieving = (User) session.getAttribute("userConnected");

        Optional<User> userOptional = repo.findByEmail(userRetrieving.getEmail());
 
        if (userOptional.isPresent()) {
            
            User u = userOptional.get();
            String devise = repo.findDeviseByEmail(u.getEmail());
            User userDestination = repo.findByEmail(transferRequestDTO.emailDestination()).orElse(null);
            // System.out.println(userDestination);
            if (userDestination != null && checkSoldeComptePrincipal(transferRequestDTO.montant()) ) {

                String paymentOption = transferRequestDTO.paymentOption();

                boolean soldeSuffisant = false;
                System.out.println(paymentOption);
            
                switch (paymentOption) {
                    case "e-transfert":
                        soldeSuffisant = checkSoldeETransfert(u, transferRequestDTO.montant());
                        break;
                    case "creditCard":
                        soldeSuffisant = checkSoldeCreditCard(u, transferRequestDTO.montant());
                        break;
                    case "accountBank":
                        soldeSuffisant = checkSoldeAccountBank(u, transferRequestDTO.montant());
                        break;
                    default:
                        break;
                }

                System.out.println(soldeSuffisant);

                if (soldeSuffisant) {
                    System.out.println(devise);
                    System.out.println(currencyRepo.findCodeByName(transferRequestDTO.deviseDestination()));

                    ResponseEntity<String> currencyApiResponse = getCurrencyData(currencyRepo.findCodeByName(devise), currencyRepo.findCodeByName(transferRequestDTO.deviseDestination()));

                    System.out.println("Currency API Response: " + currencyApiResponse.getBody());
                    double taux = 0;
                    try{
                        String jsonResponse = currencyApiResponse.getBody();
                        // Convertir la chaîne JSON en objet JSONObject
                        JSONObject jsonObject = new JSONObject(jsonResponse);
            
                        taux = jsonObject.getJSONObject("data").getDouble(currencyRepo.findCodeByName(transferRequestDTO.deviseDestination()));
            
                        // Imprimer la valeur de "EUR"
                        System.out.println("Valeur  : " + taux);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Transaction transaction = new Transaction();
                    transaction.setUserSource(userRetrieving);
                    transaction.setUserDestination(userDestination);
                    transaction.setTransaction_amount(transferRequestDTO.montant());
                    transaction.setDevise_destination(transferRequestDTO.deviseDestination());
                    transaction.setPaymentOptions(transferRequestDTO.paymentOption());
                    transaction.setDevise_source(devise);
                    transaction.setTransactionDateTime(LocalDateTime.now());
    
                    userDestination.getCompteUtilisateur().setSoldeUtilisateur(userDestination.getCompteUtilisateur().getSoldeUtilisateur() + (transferRequestDTO.montant()*taux));
    
                    userRetrieving.getCompteUtilisateur().setSoldeUtilisateur(userDestination.getCompteUtilisateur().getSoldeUtilisateur() - transferRequestDTO.montant());
    
                    transactionRepo.save(transaction);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
             }
         } else {
             return false;
         }
    }	
    

    public boolean checkSoldeETransfert(User user, double montant) {
        if( user.getCompteUtilisateur().getSoldeUtilisateur() >= montant){
            user.getCompteUtilisateur().setSoldeUtilisateur(user.getCompteUtilisateur().getSoldeUtilisateur() - montant);
            return true;
        }
        return false; 
    }
    
    public boolean checkSoldeCreditCard(User user, double montant) {
        // Double soldeCreditCard = creditCardRepo.findByUser(user);
        return creditCardRepo.findByUser(user) >= montant ; 
    }
    
    public boolean checkSoldeAccountBank(User user, double montant) {
       
        return true; 
    }
    
    public boolean addDevise(TransferRequestDeviseDTO transferRequestDeviseDTO, HttpSession session){
		User userRetrieving = (User) session.getAttribute("userConnected");

		Optional<User> user1 = repo.findByEmail(userRetrieving.getEmail()); 

		if (user1.isPresent()) {
			User user = user1.get();
			user.setDevise(transferRequestDeviseDTO.devise());
			repo.save(user);
			return true; 
		} else {
			return false; 
		}	
	}
	

    public boolean checkSoldeComptePrincipal(Double montantTransaction) {
        ComptePrincipal comptePrincipal = comptePrincipalRepo.findByCompteType("principal");
        if (comptePrincipal.getSoldePrincipal() == null || comptePrincipal.getOverdraftLimit() == null) {
            throw new IllegalStateException("Le compte principal n'est pas correctement initialisé.");
        }

        double soldeApresTransaction = comptePrincipal.getSoldePrincipal() - montantTransaction;

        if (soldeApresTransaction >= comptePrincipal.getOverdraftLimit()) {
            comptePrincipal.setSoldePrincipal(soldeApresTransaction);
            comptePrincipalRepo.save(comptePrincipal);
            return true;
        } else {
            return false;
        }
    }

    	// Fonction pour effectuer la requête vers l'API des taux de change
	public ResponseEntity<String> getCurrencyData(String deviseSource, String deviseDestination) {
		RestTemplate restTemplate = new RestTemplate();
		ApiKey apiKey = new ApiKey();

		// String apiUrl = "https://api.freecurrencyapi.com/v1/latest?apikey=" + apiKey.getKey()
		// 		+ "?base_currency=USD&currencies=EUR,USD,CAD";
        String apiUrl = "https://api.freecurrencyapi.com/v1/latest?apikey=" + apiKey.getKey() +
                    "&base_currency=" + deviseSource +
                    "&currencies=" + deviseDestination;

		return restTemplate.getForEntity(apiUrl, String.class);
	}
}
