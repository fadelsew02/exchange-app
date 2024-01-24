package com.example.exchangeApp.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.ValidationRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class validationService {
    private ValidationRepo validationRepository;
    private NotificationService notificationService;

    public void enregistrer(User utilisateur){
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger); 

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.sendEmail(validation);
    }

    public Validation lireEnFonctionDuCode(String code ){
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
}
