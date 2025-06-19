package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.entities.Plat;
import com.example.entities.RestaurantConfig;
import com.example.entities.Review;
import com.example.repositories.PlatRepository;
import com.example.repositories.RestaurantConfigRepository;
import com.example.repositories.ReviewRepository;

@Controller
public class HomeController {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<RestaurantConfig> configs = restaurantConfigRepository.findAll();
        if (!configs.isEmpty()) {
            RestaurantConfig config = configs.get(0);
            model.addAttribute("restaurantConfig", config);
        }

        List<Plat> featuredPlats = platRepository.findAll();
        if (featuredPlats.size() > 6) {
            featuredPlats = featuredPlats.subList(0, 6);
        }
        model.addAttribute("featuredPlats", featuredPlats);

        List<Review> recentReviews = reviewRepository.findAll();
        if (recentReviews.size() > 3) {
            recentReviews = recentReviews.subList(0, 3);
        }
        model.addAttribute("recentReviews", recentReviews);

        return "home";
    }
} 