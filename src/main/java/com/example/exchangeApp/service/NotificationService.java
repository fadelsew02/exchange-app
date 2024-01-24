package com.example.exchangeApp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
}