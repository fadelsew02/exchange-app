package com.example.exchangeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.exchangeApp.dto.CreditCardDTO;
import com.example.exchangeApp.model.CreditCard;
import com.example.exchangeApp.service.CreditCardService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;
    // Endpoint pour ajouter une carte de crédit
	@PostMapping("/api/addCreditCard")
	public ModelAndView AddCreditCard(@ModelAttribute CreditCard creditCard, HttpSession session) {
		CreditCardDTO creditCardDTO = new CreditCardDTO(creditCard.getCardNumber(), creditCard.getExpirationDate(), creditCard.getCheckingCode(), creditCard.getFacturationAdress(), creditCard.getCardtype());
	    
        if(creditCardService.AddCreditCard(creditCardDTO, session)){
            ModelAndView modelAndView = new ModelAndView("redirect:/mon-compte");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/mon-compte");
        modelAndView.addObject("error", "Une erreur est survenue lors de l'enregistrement de votre carte");
        return modelAndView;
	}
}
