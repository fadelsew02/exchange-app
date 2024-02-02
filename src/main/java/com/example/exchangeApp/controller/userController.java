package com.example.exchangeApp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.exchangeApp.dto.AuthenticationDTO;

import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.security.JwtService;
import com.example.exchangeApp.service.userService;

import com.example.exchangeApp.model.ApiKey;

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

		if (service.saveUsers(user)) {
			ModelAndView modelAndView = new ModelAndView("redirect:/confirmation");
			modelAndView.addObject("validation", new Validation());

			return modelAndView;
			// return "redirect:/confirmation";
		}

		return null;

	}

	// Endpoint pour l'activation de compte
	@PostMapping("/api/activation")
	public ModelAndView activate(@ModelAttribute Validation validation) {
		Map<String, String> activation = new HashMap<>();
		activation.put("code", validation.getCode());
		if (this.service.activation(activation)) {
			ModelAndView modelAndView = new ModelAndView("redirect:/login-and-register");
			// modelAndView.addObject("Message", "Votre inscription est réussi.");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/confirmation");
		modelAndView.addObject("error", "Votre code a expiré");
		return modelAndView;
	}

	// Endpoint pour se connecter dans l'application
	@PostMapping("/api/connexion")
	public ModelAndView login(@ModelAttribute User user, HttpSession session) {
		AuthenticationDTO authenticationDTO = new AuthenticationDTO(user.getEmail(), user.getPassword());
		final Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));
		if (authenticate.isAuthenticated()) {
			Map<String, String> token = this.jwtService.generate(authenticationDTO.username());
			Optional<User> user_con = this.service.getUserByEmail(this.jwtService.extractUsername(token.get("bearer")));
			if (user_con.isPresent()) {
				User userConnected = user_con.get();
				session.setAttribute("userConnected", userConnected);
			}

			ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
			return modelAndView;
			// return token;

		}
		return null;
	}

	@PostMapping(path = "refresh-token")
	public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
		return this.jwtService.refreshToken(refreshTokenRequest);
	}

	@GetMapping("/deconnexion")
	public ModelAndView logout(HttpSession session) {
		if (this.jwtService.deconnexion(session)) {
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			session.removeAttribute("userConnected");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}

	public User getUserConnected(HttpSession session) {
		User userConnected = (User) session.getAttribute("userConnected");
		return userConnected;
	}

	// private String userSourceEmail;
	// private String userDestinationEmail;
	// private Double transactionAmount;
	// private String deviseSource;
	// private String deviseDestination;
}
