package com.example.exchangeApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.exchangeApp.model.taux_change;
import com.example.exchangeApp.repo.tauxDeChangeRepo;

    public class tauxChangeService {


        @Autowired
        private tauxDeChangeRepo tauxDeChangeRepository;

        public double tauxdechange(String deviseSource, String deviseDestination) {
            taux_change tauxDeChange = tauxDeChangeRepository.findByDeviseSourceAndDeviseDestination(deviseSource, deviseDestination);

            if (tauxDeChange != null) {
                return tauxDeChange.getTaux();
            } else {
                // Gérer le cas où le taux de change n'est pas trouvé
                return 0.0;
                // throw new TauxDeChangeNotFoundException("Taux de change non trouvé pour la paire de devises.");
            }
        }
	}

