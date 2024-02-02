// package com.example.exchangeApp.service;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.example.exchangeApp.model.Currency;

// @Service
// public class DeviseService {
    
//     private final List<Currency> currencies;

//     public DeviseService() {
//         // Initialisez votre liste de devises ici
//         this.currencies = new ArrayList<>();
//         currencies.add(new Currency("EUR", "Euro"));
//         currencies.add(new Currency("USD", "US Dollar"));
//         currencies.add(new Currency("CFA", "Francs CFA"));
// 		currencies.add(new Currency("JPY", "Japanese Yen"));
//         currencies.add(new Currency("BGN", "Bulgarian Lev"));
// 		currencies.add(new Currency("CZK", "Czech Republic Koruna"));
//         currencies.add(new Currency("DKK", "Danish Krone"));
// 		currencies.add(new Currency("GBP", "British Pound Sterling"));
//         currencies.add(new Currency("HUF", "Hungarian Forint"));
// 		currencies.add(new Currency("PLN", "Polish Zloty"));
//         currencies.add(new Currency("RON", "Romanian Leu"));
// 		currencies.add(new Currency("SEK", "Swedish Krona"));
//         currencies.add(new Currency("CHF", "Swiss Franc"));
// 		currencies.add(new Currency("ISK", "Icelandic Króna"));
//         currencies.add(new Currency("NOK", "Norwegian Krone"));
// 		currencies.add(new Currency("HRK", "Croatian Kuna"));
//         currencies.add(new Currency("RUB", "Russian Ruble"));
// 		currencies.add(new Currency("HKD", "Hong Kong Dollar"));
//         currencies.add(new Currency("TRY", "Turkish Lira"));
// 		currencies.add(new Currency("AUD", "Australian Dollar"));
//         currencies.add(new Currency("BRL", "Brazilian Real"));
// 		currencies.add(new Currency("CAD", "Canadian Dollar"));
//         currencies.add(new Currency("CNY", "Chinese Yuan"));
// 		currencies.add(new Currency("IDR", "Indonesian Rupiah"));
// 		currencies.add(new Currency("ILS", "Israeli New Sheqel"));
//         currencies.add(new Currency("INR", "Indian Rupee"));
// 		currencies.add(new Currency("KRW", "South Korean Won"));
//         currencies.add(new Currency("MXN", "Mexican Peso"));
// 		currencies.add(new Currency("SGD", "Singapore Dollar"));


//     }

//     public String findCodeByNom(String nom) {
//         System.out.println()
//         for (Currency currency : currencies) {
//             if (currency.getName().equals(nom)) {
//                 return currency.getCode();
//             }
//         }
//         throw new IllegalArgumentException("Code de devise non trouvé pour le nom : " + nom);
//     }
// }
