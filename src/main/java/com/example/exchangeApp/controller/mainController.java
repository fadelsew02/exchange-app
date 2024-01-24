package com.example.exchangeApp.controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")

public class mainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/monnaies")
    public String monnaies() {
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

    @GetMapping("/login-and-register")
    public String showRegisterPage() {
        return "LoginAndRegister"; 
    }
}





