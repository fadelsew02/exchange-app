package com.example.exchangeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.exchangeApp.dto.CreditCardDTO;
import com.example.exchangeApp.dto.SoldOperationDTO;
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
		CreditCardDTO creditCardDTO = new CreditCardDTO(creditCard.getCardNumber(), creditCard.getExpirationDate(), creditCard.getCheckingCode(), creditCard.getFacturationAdress());
	    
        if(creditCardService.AddCreditCard(creditCardDTO, session)){
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        modelAndView.addObject("error", "Une erreur est survenue lors de l'enregistrement de votre carte");
        return modelAndView;
	}

    @PostMapping("/api/soldoperation")
    public ModelAndView performSoldOperation(@ModelAttribute SoldOperationDTO soldOperationDTO, HttpSession session) {
        try {
            
            if (creditCardService.performSoldOperation(soldOperationDTO, session)) {
                ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
                modelAndView.addObject("error", "Une erreur est survenue lors de l'opération sur votre solde.");
                return modelAndView;
            }
        } catch (Exception e) { 
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            modelAndView.addObject("error", "Une erreur est survenue lors de l'opération sur votre solde.");
            return modelAndView;
        }
    }




}


