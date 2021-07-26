package com.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@RequestParam(name="name", required = false, defaultValue = "User") String name, Model model) {
        model.addAttribute("title", "Main page");
        model.addAttribute("name", name);
        return "home";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        System.out.println("GET-method returns login.html view");
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return "login";
    }

    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logoutPage(){
        return "logout";
    }

}