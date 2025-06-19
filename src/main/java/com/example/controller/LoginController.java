package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.entities.RestaurantConfig;
import com.example.repositories.RestaurantConfigRepository;

@Controller
public class LoginController {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @GetMapping("/login")
    public String login(Model model) {
        List<RestaurantConfig> configs = restaurantConfigRepository.findAll();
        if (!configs.isEmpty()) {
            model.addAttribute("restaurantConfig", configs.get(0));
        }
        return "login";
    }
} 