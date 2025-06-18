package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class RestaurantConfigController {


    @GetMapping("/config") 
    public String showConfigForm(Model model) {
       
        return "configForm"; 
    }

  
}
