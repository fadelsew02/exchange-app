package com.example.exchangeApp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
// import lombok.extern.slf4j.Slf4j;
// import javax.xml.bind.ValidationException;
import org.springframework.security.core.AuthenticationException;

import com.example.exchangeApp.dto.AuthenticationDTO;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.security.JwtService;
import com.example.exchangeApp.service.userService;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)

public class userController {

    @Autowired
	private userService service;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
    // Endpoint pour s'enregistrer
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User utilisateur) {
    	try {
	        if (service.saveUsers(utilisateur)) {
	            return ResponseEntity.ok("Enregistrement réussi");
	        } else {
	            // Peut-être un échec de validation ou une autre raison non spécifiée
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'enregistrement");
	        }
	    } catch (DuplicateKeyException e) {
	        // Violation d'une contrainte de clé primaire ou unique
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur avec cet email existe déjà");
	    } catch (DataAccessException e) {
	        // Problèmes d'accès aux données ou d'intégrité
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'enregistrement en raison d'une erreur interne");
	    } 
    }

	// Endpoint pour l'activation
    @PostMapping("/activation")
    public void activate(@RequestBody Map<String, String> activation) {
    	this.service.activation(activation);
    }


    //Endpoint pour se connecter
    // @PostMapping("/login")
	// public ResponseEntity<String> login(@RequestBody AuthenticationDTO authenticationDTO) {
	//     try {
	//         if (service.connectUsers(utilisateur) != null) {
	//             return ResponseEntity.ok("Connexion réussie");
	//         } else {
	//             // Utilisateur non trouvé
	//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
	//         }
	//     } catch (AuthenticationException e) {
	//         // Problèmes d'authentification
	//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification : " + e.getMessage());
	//     } catch (Exception e) {
	//         // Gestion d'autres exceptions non prévues
	//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la connexion");
	//     }
	// }
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
	    final Authentication authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
		);

		if(authenticate.isAuthenticated()){
			return this.jwtService.generate(authenticationDTO.username());
		}
		return null;
	}
}



