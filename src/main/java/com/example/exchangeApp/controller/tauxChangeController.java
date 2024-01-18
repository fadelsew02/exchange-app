package com.example.exchangeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.example.exchangeApp.model.taux_change;
// import com.example.exchangeApp.model.user;
import com.example.exchangeApp.service.tauxChangeService;

@RestController

public class tauxChangeController {
    @Autowired
	private tauxChangeService service;

        public Double calculateTauxDeChange(String deviseSource, String deviseDestination) {
            Double taux = service.tauxdechange(deviseSource, deviseDestination);
    	if( taux != 0){
    		return taux;
    	} 
        return 0.0;
    }
}
