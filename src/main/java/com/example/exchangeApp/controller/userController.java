package com.example.exchangeApp.controller;
// import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import javax.xml.bind.ValidationException;
// import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.ui.Model;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import com.example.exchangeApp.dto.AuthenticationDTO;
import com.example.exchangeApp.dto.TransferRequestDTO;
import com.example.exchangeApp.dto.TransferRequestDeviseDTO;
import com.example.exchangeApp.model.TransactionRequest;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.security.JwtService;
import com.example.exchangeApp.service.userService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping

public class userController {

	Map<String, String> tokenUser;
    @Autowired
	private userService service;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	UserDetailsService userDetailsService;

    // Endpoint pour s'enregistrer dans l'application
    @PostMapping("/api/inscription")
    public ModelAndView register(@ModelAttribute User user) {
    	try {
	        service.saveUsers(user);
			ModelAndView modelAndView = new ModelAndView("confirmation");
			modelAndView.addObject("message", "Inscription réussie!Veuillez confirmer le code");

		    return modelAndView;       
	    } catch (DuplicateKeyException e) {
			ModelAndView modelAndView = new ModelAndView("register");
			modelAndView.addObject("error",  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'user avec cet email existe déjà"));

			return modelAndView;
	    } catch (DataAccessException e) {
			ModelAndView modelAndView = new ModelAndView("register");
			modelAndView.addObject("error", ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'enregistrement en raison d'une erreur interne"));

			return modelAndView;
	    } 
    }

	// Endpoint pour l'activation de compte
    @PostMapping("/api/activation")
    public ModelAndView activate(@ModelAttribute Validation validation) {
		Map<String, String> activation = new HashMap<>();
    	activation.put("code", validation.getCode());
		if(this.service.activation(activation)){
			ModelAndView modelAndView = new ModelAndView("dashboard");
			// modelAndView.addObject("error", "Votre code a expiré");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("confirmation");
		modelAndView.addObject("error", "Votre code a expiré");
			return modelAndView;
    }

	// Endpoint pour se connecter dans l'application
	@PostMapping("/api/connexion")
	public Map<String, String> login(@ModelAttribute User user, HttpSession session) {
		AuthenticationDTO authenticationDTO = new AuthenticationDTO(user.getEmail(), user.getPassword());
	    final Authentication authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
		);
		if(authenticate.isAuthenticated()){
			session.setAttribute("loggedInUser", user);
			Map<String, String> token = this.jwtService.generate(authenticationDTO.username());
			getToken(token);
			// User loggedInUser = (User) session.getAttribute("loggedInUser");  
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
			// modelAndView.addObject("loggedInUser", loggedInUser);
			// modelAndView.addObject("TransactionRequest", new TransactionRequest());
			// modelAndView.addObject("TransactionRequestDevise", new TransactionRequest());

			// return modelAndView;
			return token;
			
		}
		return null;
	}

	@PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

	
    @PostMapping("/deconnexion")
	public void logout(HttpServletRequest request) {
		this.jwtService.deconnexion(request);
	}


	public void getToken(Map<String, String> token) {
		tokenUser = token;
	}

	

	@PostMapping("/choose-devise")
	public ModelAndView chooseDevise(@ModelAttribute TransactionRequest transactionRequestDevise) {
		TransferRequestDeviseDTO transferRequestDeviseDTO = new TransferRequestDeviseDTO(transactionRequestDevise.getDeviseSource());
	    try {
			this.service.addDevise(transferRequestDeviseDTO);
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		modelAndView.addObject("devise", transferRequestDeviseDTO.devise());
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		modelAndView.addObject("error", "Une erreur est survenue");
			return modelAndView;
		}
	}

	// private String userSourceEmail;
    // private String userDestinationEmail;
    // private Double transactionAmount;
    // private String deviseSource;
    // private String deviseDestination;
	

	// @PostMapping("/transfer-money")
	// public ModelAndView transferMoney(@ModelAttribute TransactionRequest transactionRequest) {
	// 	TransferRequestDTO transferRequestDTO = new TransferRequestDTO(transactionRequest.getUserDestinationEmail(), transactionRequest.getTransactionAmount(), transactionRequest.getDeviseDestination());
	//     try {
	// 		this.service.transferMoney(transferRequestDTO);
	// 		ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
	// 	modelAndView.addObject("devise", transferRequestDeviseDTO.devise());
	// 		return modelAndView;
	// 	} catch (Exception e) {
	// 		ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
	// 	modelAndView.addObject("error", "Une erreur est survenue");
	// 		return modelAndView;
	// 	}
	// }

	
	
}



