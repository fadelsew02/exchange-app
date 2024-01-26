package com.example.exchangeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.exchangeApp.model.TransactionRequest;
import com.example.exchangeApp.service.secondaryAccountService;

@RestController
public class secondaryAccountController {

	@Autowired
	private secondaryAccountService service;

	@PostMapping("/trans-money")
    public ResponseEntity<String> transferMoney(@RequestBody TransactionRequest transactionRequest) {
			
		if(service.pullTransaction(transactionRequest)){
			return ResponseEntity.ok("Transaction successful");
		}

		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Insufficient funds for the transfer");
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
