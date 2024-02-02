package com.example.exchangeApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.exchangeApp.dto.TransferRequestDTO;
import com.example.exchangeApp.dto.TransferRequestDeviseDTO;
import com.example.exchangeApp.model.ApiKey;
import com.example.exchangeApp.model.ApiResponse;
import com.example.exchangeApp.model.TransactionRequest;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.service.transactionService;
import com.example.exchangeApp.service.userService;

import jakarta.servlet.http.HttpSession;

@RestController
public class transactionController {

    @Autowired
	private userService service;
    @Autowired
	private transactionService transactionService;

    
    @GetMapping("/api/check-email-existence")
    public ApiResponse checkEmailExistence(@RequestParam String email) {
        System.out.println(email);
        Optional<User> userFound = service.getUserByEmail(email);
        ApiResponse response = new ApiResponse();

        if(userFound.isPresent()){
            response.setExists(true);
            String devise = service.getDeviseByEmail(email);
            response.setDevise(devise);

            return response;
        }
        
        response.setExists(false);
        return response;
    }

    @PostMapping("/choose-devise")
	public ModelAndView chooseDevise(@ModelAttribute TransactionRequest transactionRequestDevise, HttpSession session) {
		TransferRequestDeviseDTO transferRequestDeviseDTO = new TransferRequestDeviseDTO(transactionRequestDevise.getDeviseSource());
	    try {
			this.transactionService.addDevise(transferRequestDeviseDTO, session);
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
			modelAndView.addObject("error", "Une erreur est survenue");
			return modelAndView;
		}
	}

    @PostMapping("/transfer-money")
	public ModelAndView transferMoney(@ModelAttribute TransactionRequest transactionRequest, HttpSession session) {
		TransferRequestDTO transferRequestDTO = new TransferRequestDTO(transactionRequest.getUserDestinationEmail(), transactionRequest.getTransactionAmount(), transactionRequest.getDeviseDestination(), transactionRequest.getPaymentOption());
	    try {
			this.transactionService.transferMoney(transferRequestDTO, session);
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		    modelAndView.addObject("error", "Une erreur est survenue");
			return modelAndView;
		}
	}
}
