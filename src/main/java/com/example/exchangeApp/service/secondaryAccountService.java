package com.example.exchangeApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exchangeApp.model.SecondaryAccount;
import com.example.exchangeApp.model.Transaction;
import com.example.exchangeApp.model.TransactionRequest;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.secondaryAccountRepo;
import com.example.exchangeApp.repo.transactionRepo;

@Service
public class secondaryAccountService {

    @Autowired
    secondaryAccountRepo secondaryAccountRepo;
    transactionRepo transactionRepo;
    userService service;

    public SecondaryAccount getSecondaryAccountById(Long id) {
        return secondaryAccountRepo.findById(id).get();
    }

    public boolean compareAmountInAccount(Transaction Transaction) {
        Double amountRemaining = secondaryAccountRepo.findAmountByDevise(Transaction.getDevise_destination());

        if (amountRemaining <= Transaction.getTransaction_amount()) {
            return false;
        }

        Double reliquat = amountRemaining - Transaction.getTransaction_amount();

        SecondaryAccount updateObj = secondaryAccountRepo.findByDevise(Transaction.getDevise_destination());
        updateObj.setAmount(reliquat);
        secondaryAccountRepo.save(updateObj);
        transactionRepo.save(Transaction);

        return true;
    }

    public boolean pullTransaction(TransactionRequest transactionRequest) {

        Transaction transaction = new Transaction();
        Optional<User> userSource = service.getUserByEmail(transactionRequest.getUserSourceEmail());
        Optional<User> userDestination = service.getUserByEmail(transactionRequest.getUserDestinationEmail());

        if (userSource.isPresent() && userDestination.isPresent()) {
            User user1 = userSource.get();
            User user2 = userDestination.get();

            transaction.setUserSource(user1);
            transaction.setUserDestination(user2);
            transaction.setTransaction_amount(transactionRequest.getTransactionAmount());
            transaction.setDevise_source(transactionRequest.getDeviseSource());
            transaction.setDevise_destination(transactionRequest.getDeviseDestination());

            return compareAmountInAccount(transaction);
        }

        return false;
    }

}
