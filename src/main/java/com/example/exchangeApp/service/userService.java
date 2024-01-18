package com.example.exchangeApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exchangeApp.model.user;
import com.example.exchangeApp.repo.userRepo;


@Service
public class userService {

    @Autowired
	userRepo repo;

    public user getToDoItemById(Long id) {
		return repo.findById(id).get();
	}

    public boolean saveUsers(user utilisateur) {

        user userSaved = repo.save(utilisateur);
		
		if (getToDoItemById(userSaved.getId()) != null) {
			return true;
		}
		return false;
	}

    public user connectUsers(user utilisateur) {
        user userFound = null;
        userFound = repo.findByEmail(utilisateur.getEmail());    

        return userFound;
        // if(userFound != null){
        //     if(userFound.getPassword() == utilisateur.getPassword()){
        //         return true;
        //     }
        // }
        // return false;
	}
}






