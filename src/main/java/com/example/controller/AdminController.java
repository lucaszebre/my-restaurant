package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.repositories.RestaurantConfigRepository;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @GetMapping("")
    public String adminDashboard(Model model) {
        var restaurantConfig = restaurantConfigRepository.findFirstByOrderByIdAsc();
        if (restaurantConfig != null && restaurantConfig.isConfigured()) {
            model.addAttribute("restaurantName", restaurantConfig.getRestaurantName());
            model.addAttribute("restaurantConfig", restaurantConfig);
        }
        
        return "admin/dashboard";
    }
} 