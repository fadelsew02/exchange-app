package com.example.exchangeApp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.example.exchangeApp.model.User;
import com.example.exchangeApp.model.Validation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")

public class mainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/money")
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
        User loggedInUser = (User) session.getAttribute("loggedInUser");    
        model.addAttribute("loggedInUser", loggedInUser);

        return "dashboard";
    }

    @GetMapping("/mon-compte")
    public String compte(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");    
        model.addAttribute("loggedInUser", loggedInUser);

        return "compte";
    }

    @GetMapping("/mes-transactions")
    public String transactions(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");    
        model.addAttribute("loggedInUser", loggedInUser);

        return "transaction";
    }

}





