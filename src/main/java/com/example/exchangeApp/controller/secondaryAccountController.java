package com.example.exchangeApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exchangeApp.model.user;
import com.example.exchangeApp.service.userService;


public class secondaryAccountController {
    
}






@RestController
@RequestMapping("/")

public class userController {

    @Autowired
	private userService service;

    // Endpoint pour s'enregistrer
    @PostMapping("/register")
    public boolean register(@RequestBody user utilisateur) {
    	if(service.saveUsers(utilisateur)){
    		return true;
    	} else {
    		return false;
    	}
    }

    // Endpoint pour se connecter
    @PostMapping("/login")
    public boolean login(@RequestBody user utilisateur) {
    	if(service.connectUsers(utilisateur) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    
}






// public class BookController {

	
// 	private List<Book> books = new ArrayList<>();

// 	// Endpoint pour récupérer tous les livres
// 	@GetMapping
// 	public List<Book> seeAllBooks() {
// 		books = service.getAllBookItems();
// 		return books;
// 	}

// 	// Endpoint pour récupérer un livre par son ID
//     @GetMapping("/{bookId}")
//     public Book getBookById(@PathVariable Long bookId) {
//     	return service.getBookItemById(bookId);
//     }



//     // Endpoint pour supprimer un livre
//     @DeleteMapping("/{bookId}")
//     public boolean deleteBook(@PathVariable Long bookId) {
//         if(service.deleteBookItem(bookId)) {
//         	return true;
//         } else {
//         	return false;
//         }
//     }

//     // Endpoint pour mettre à jour un livre existant
//     @PutMapping("/{bookId}")
//     public String updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
//         if(service.updateBookItem(bookId, updatedBook)){
//         	return "Book updated";
//         } else {
//         	return "Book not updated";
//         }
        
//     }
// }
