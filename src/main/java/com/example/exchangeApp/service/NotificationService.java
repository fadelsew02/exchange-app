package com.example.exchangeApp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotificationService {
    JavaMailSender javaMailSender;
    public void sendEmail(Validation validation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@example.com");
        mailMessage.setTo(validation.getUtilisateur().getEmail());
        mailMessage.setSubject(("Votre code d'activation"));

        String text = String.format("Bonjour %s <br /> Votre code d'activation est %s. A bientôt", validation.getUtilisateur().getFirstName(),validation.getCode());

        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }


        // Nouvelle méthode pour envoyer un email lors d'un transfert
    public void sendTransferNotification(User userDestination, User userSource, Double receivedAmount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@example.com");
        mailMessage.setTo(userDestination.getEmail());
        mailMessage.setSubject("Nouveau transfert reçu");
    
        String text = String.format("Bonjour %s,\nVous avez reçu un transfert de %s %s de la part de %s.",
        userDestination.getFirstName(), receivedAmount, userDestination.getDevise(),
        userSource.getFirstName());
    
        mailMessage.setText(text);
    
        javaMailSender.send(mailMessage);
    }
}