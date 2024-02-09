package com.example.exchangeApp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.model.CompteUtilisateur;
import com.example.exchangeApp.model.Role;
import com.example.exchangeApp.TypeDeRole;
import com.example.exchangeApp.dto.TransactionInfoDTO;
import com.example.exchangeApp.repo.userRepo;
import com.example.exchangeApp.repo.transactionRepo;
import com.example.exchangeApp.service.transactionService;
import com.example.exchangeApp.repo.CurrencyRepo;

import jakarta.persistence.Column;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.List;

import java.util.Optional;

@AllArgsConstructor
// @NoArgsConstructor
@Service
public class userService implements UserDetailsService {

	@Autowired
	CurrencyRepo currencyRepo;
    @Autowired
	userRepo repo;
	@Autowired
	transactionService transactionService;
	@Autowired
	transactionRepo transactionRepo;
	private validationService validationService;
	private BCryptPasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
		return repo.findById(id).get();
	}

    public Optional<User> getUserByEmail(String email) {
		return repo.findByEmail(email);
	}

	public String getDeviseByEmail(String email) {
		return repo.findDeviseByEmail(email);
	}

	 public User updateUser(User user) {
        
        // Récupérer l'utilisateur existant depuis la base via son id
        User existingUser = getUserById(user.getId());
        
        // Mettre à jour les propriétés modifiées
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSurName(user.getSurName());
        existingUser.setEmail(user.getEmail());
        existingUser.setTel(user.getTel());
        
        // Sauvegarder dans la base
        User updatedUser = repo.save(existingUser);
        
        return updatedUser;
    }

    public boolean saveUsers(User user) {

		final Optional<User> userOptional = repo.findByEmail(user.getEmail());
    	if(!user.getEmail().contains("@") || !user.getEmail().contains(".") || userOptional.isPresent()){
    		throw new RuntimeException("Invalid mail");
    	}
		
    	final String mdpCrypte = this.passwordEncoder.encode(user.getPassword());
    	user.setPassword(mdpCrypte);

    	final Role roleUser = new Role();
    	roleUser.setLibelle(TypeDeRole.UTILISATEUR); 
		if (user.getRole() != null && user.getRole().getLibelle().equals(TypeDeRole.ADMINISTRATEUR)) {
            roleUser.setLibelle(TypeDeRole.ADMINISTRATEUR);
            user.setActif(true);
        }
    	user.setRole(roleUser);

		CompteUtilisateur compteUtilisateur = new CompteUtilisateur();
		compteUtilisateur.setSoldeUtilisateur(5000.0);

		user.setCompteUtilisateur(compteUtilisateur);

		user = this.repo.save(user);

		if (roleUser.getLibelle().equals(TypeDeRole.UTILISATEUR)) {
            this.validationService.enregistrer(user);
        }
		return true;
	}

	public boolean activation(Map<String, String> activation) {
		final Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
		if(Instant.now().isAfter(validation.getExpiration())){
			throw new RuntimeException("Votre code a expiré");
		}
		final User utilisateurActiver = this.repo.findById(validation.getUtilisateur().getId()).orElseThrow(()-> new RuntimeException("Utilisateur inconnu"));
		utilisateurActiver.setActif(true);
		this.repo.save(utilisateurActiver);

		return true;
	}

	public Double getSoldeUtilisateurConnecte(HttpSession session) {
		User userConnected = (User) session.getAttribute("userConnected");
        Double solde = userConnected.getCompteUtilisateur().getSoldeUtilisateur();

    	// Arrondir le solde à trois chiffres après la virgule
    	double soldeArrondi = Math.round(solde * 1000.0) / 1000.0;
	    return soldeArrondi;
    }

    public String getDeviseUtilisateurConnecte(HttpSession session) {
		User userConnected = (User) session.getAttribute("userConnected");

	    return currencyRepo.findCodeByName(userConnected.getDevise());
    }

    public Double getSommeEnvoisUtilisateur(HttpSession session) {
    	User userConnected = (User) session.getAttribute("userConnected");
        return transactionRepo.findSommeEnvoisByUserEmail(userConnected.getEmail());
    }

    public Double getSommeReceptionsUtilisateur(HttpSession session) {
        User userConnected = (User) session.getAttribute("userConnected");
        return transactionRepo.findSommeReceptionsByUserEmail(userConnected.getEmail());
    }

    public List<TransactionInfoDTO> getAllTransactionsForUser(HttpSession session) {
    	User userConnected = (User) session.getAttribute("userConnected");

        return transactionService.findTransactionsInfoByUserId(userConnected.getId());
    }

	@Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.repo
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }
}






