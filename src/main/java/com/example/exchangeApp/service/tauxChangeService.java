package com.example.exchangeApp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exchangeApp.model.TauxChange;
import com.example.exchangeApp.repo.tauxDeChangeRepo;


@Service
    public class tauxChangeService {


        @Autowired
        private tauxDeChangeRepo tauxDeChangeRepository;

        // @Autowired
        // private TauxDeChangeRepo tauxDeChangeRepo;
    
        public Double findTauxByDeviseSourceAndDeviseDestination(String deviseSource, String deviseDestination) {
            TauxChange tauxChange = tauxDeChangeRepository.findByDeviseSourceAndDeviseDestination(deviseSource, deviseDestination);
            return (tauxChange != null) ? tauxChange.getTaux() : null;
        }

        // public double tauxdechange(String deviseSource, String deviseDestination) {
        //     taux_change tauxDeChange = tauxDeChangeRepository.findByDeviseSourceAndDeviseDestination(deviseSource, deviseDestination);

        //     if (tauxDeChange != null) {
        //         return tauxDeChange.getTaux();
        //     } else {
        //         // Gérer le cas où le taux de change n'est pas trouvé
        //         return 0.0;
        //         // throw new TauxDeChangeNotFoundException("Taux de change non trouvé pour la paire de devises.");
        //     }
        // }
	}

