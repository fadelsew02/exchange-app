package com.example.exchangeApp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.example.exchangeApp.model.Currency;
import com.example.exchangeApp.model.SoldOperation;
import com.example.exchangeApp.model.CreditCard;
import com.example.exchangeApp.model.TransactionRequest;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;
import com.example.exchangeApp.service.BankService;
import com.example.exchangeApp.service.CurrencyService;
import com.example.exchangeApp.service.userService;
import com.example.exchangeApp.dto.TransactionInfoDTO;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")

public class mainController{

    @Autowired
	BankService bankService;
    @Autowired
    userService service;
    @Autowired
	CurrencyService currencyService;
 

    @GetMapping("/")
    public String index() {

        boolean bool = bankService.SaveBank();
        boolean bool2 = currencyService.SaveCurrency();
        return "index";
    }
    
    @GetMapping("/monnaies")
    public String money() {
        return "monnaies";
    }

    @GetMapping("/reviews")
    public String review() {
        return "reviews";
    }

    @GetMapping("/contact")
    public String service() {
        return "contact";
    }

    @GetMapping("/confirmation")
    public ModelAndView confirmation(ModelAndView modelAndView) {
        modelAndView = new ModelAndView("confirmation");
		modelAndView.addObject("validation",new Validation());

		return modelAndView;
    }

    @GetMapping("/login-and-register")
    public ModelAndView showRegisterPage(ModelAndView modelAndView) {
        modelAndView = new ModelAndView("LoginAndRegister");
		modelAndView.addObject("user",new User());

		return modelAndView;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("EUR", "Euro"));
        currencies.add(new Currency("USD", "US Dollar"));
        currencies.add(new Currency("CFA", "Francs CFA"));
		currencies.add(new Currency("JPY", "Japanese Yen"));
        currencies.add(new Currency("BGN", "Bulgarian Lev"));
		currencies.add(new Currency("CZK", "Czech Republic Koruna"));
        currencies.add(new Currency("DKK", "Danish Krone"));
		currencies.add(new Currency("GBP", "British Pound Sterling"));
        currencies.add(new Currency("HUF", "Hungarian Forint"));
		currencies.add(new Currency("PLN", "Polish Zloty"));
        currencies.add(new Currency("RON", "Romanian Leu"));
		currencies.add(new Currency("SEK", "Swedish Krona"));
        currencies.add(new Currency("CHF", "Swiss Franc"));
		currencies.add(new Currency("ISK", "Icelandic Króna"));
        currencies.add(new Currency("NOK", "Norwegian Krone"));
		currencies.add(new Currency("HRK", "Croatian Kuna"));
        currencies.add(new Currency("RUB", "Russian Ruble"));
		currencies.add(new Currency("HKD", "Hong Kong Dollar"));
        currencies.add(new Currency("TRY", "Turkish Lira"));
		currencies.add(new Currency("AUD", "Australian Dollar"));
        currencies.add(new Currency("BRL", "Brazilian Real"));
		currencies.add(new Currency("CAD", "Canadian Dollar"));
        currencies.add(new Currency("CNY", "Chinese Yuan"));
		currencies.add(new Currency("IDR", "Indonesian Rupiah"));
		currencies.add(new Currency("ILS", "Israeli New Sheqel"));
        currencies.add(new Currency("INR", "Indian Rupee"));
		currencies.add(new Currency("KRW", "South Korean Won"));
        currencies.add(new Currency("MXN", "Mexican Peso"));
		currencies.add(new Currency("SGD", "Singapore Dollar"));

        Currency currency = new Currency();
        for (Currency curren : currencies) {
            currency.setCode(curren.getCode());
            currency.setName(curren.getName());
        }
        User userConnected = (User) session.getAttribute("userConnected");
        System.out.println(userConnected);

        model.addAttribute("User", userConnected);
        model.addAttribute("TransactionRequest", new TransactionRequest());
        model.addAttribute("creditCard", new CreditCard());
        model.addAttribute("soldOperation", new SoldOperation());
		model.addAttribute("TransactionRequestDevise", new TransactionRequest());
        model.addAttribute("currencies", currencies);




        return "dashboard";
    }


    @GetMapping("/mes-transactions")
    public ModelAndView transactions(ModelAndView modelAndView, HttpSession session) {
        List<TransactionInfoDTO> allTransactions = service.getAllTransactionsForUser(session);
        System.out.println(allTransactions);
        modelAndView = new ModelAndView("transaction");
        modelAndView.addObject("AllTransactions", allTransactions);
        return modelAndView;
    }

    @GetMapping("/transfer-money")
    public String transferMoney(Model model) {
       	model.addAttribute("TransactionRequest", new TransactionRequest());
		model.addAttribute("TransactionRequestDevise", new TransactionRequest());

        return "transaction";
    }

}
