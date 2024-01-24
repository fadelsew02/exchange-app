package com.example.exchangeApp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.model.Role;
import com.example.exchangeApp.TypeDeRole;
import com.example.exchangeApp.repo.userRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class userService implements UserDetailsService {

    @Autowired
	userRepo repo;
	private validationService validationService;
	private BCryptPasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
		return repo.findById(id).get();
	}

    public Optional<User> getUserByEmail(String email) {
		return repo.findByEmail(email);
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

        user = this.repo.save(user);

		if (roleUser.getLibelle().equals(TypeDeRole.UTILISATEUR)) {
            this.validationService.enregistrer(user);
        }
		return true;
	}

	public void activation(Map<String, String> activation) {

		final Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
		if(Instant.now().isAfter(validation.getExpiration())){
			throw new RuntimeException("Votre code a expiré");
		}
		final User utilisateurActiver = this.repo.findById(validation.getUtilisateur().getId()).orElseThrow(()-> new RuntimeException("Utilisateur inconnu"));
		utilisateurActiver.setActif(true);
		this.repo.save(utilisateurActiver);
	}

	@Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.repo
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }

    // public Optional<User> connectUsers(User user) {
    //     Optional<User> UserFound = null;
    //     UserFound = repo.findByEmail(user.getEmail());    

    //     return UserFound;
    //     // if(UserFound != null){
    //     //     if(UserFound.getPassword() == user.getPassword()){
    //     //         return true;
    //     //     }
    //     // }
    //     // return false;
	// }
}






