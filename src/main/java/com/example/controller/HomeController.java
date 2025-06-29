package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.repositories.RestaurantConfigRepository;
import com.example.repositories.CarteRepository;
import com.example.repositories.MenuRepository;
import com.example.repositories.ReviewRepository;
import com.example.repositories.PlatRepository;
import com.example.entities.RestaurantConfig;
import com.example.entities.Carte;
import com.example.entities.Menu;
import com.example.entities.MenuSection;
import com.example.entities.Review;
import com.example.entities.Plat;
import com.example.component.ColorUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashSet;

@Controller
public class HomeController {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;
    
    @Autowired
    private CarteRepository carteRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlatRepository platRepository;

    @GetMapping("/")
    public String home(Model model) {
        try {
            RestaurantConfig config = restaurantConfigRepository.findFirstByOrderByIdAsc();
            
            if (config == null) {
                return "redirect:/config";
            }
            
            if (config.getRestaurantName() == null || config.getRestaurantName().isEmpty()) {
                config.setRestaurantName("Mon Restaurant");
            }
            
            List<Review> latestReviews = reviewRepository.findTop4ByOrderByDateDesc(PageRequest.of(0, 4));
            
            model.addAttribute("config", config);
            model.addAttribute("title", config.getRestaurantName());
            model.addAttribute("latestReviews", latestReviews);
            
            return "home";
        } catch (Exception e) {
            return "redirect:/config";
        }
    }
    
    @GetMapping("/carte")
    public String carte(Model model) {
        try {
            RestaurantConfig config = restaurantConfigRepository.findFirstByOrderByIdAsc();
            
            if (config == null) {
                return "redirect:/config";
            }
            
            if (config.getRestaurantName() == null || config.getRestaurantName().isEmpty()) {
                config.setRestaurantName("Mon Restaurant");
            }
            
            List<Carte> cartes = carteRepository.findAll();
            System.out.println("Found " + cartes.size() + " cartes");
            
            for (Carte carte : cartes) {
                if (carte.getPlats() != null) {
                    int platCount = carte.getPlats().size();
                    System.out.println("Carte '" + carte.getName() + "' has " + platCount + " plats");
                } else {
                    System.out.println("Carte '" + carte.getName() + "' has no plats");
                }
            }
            
            List<Menu> allMenus = menuRepository.findByActiveTrue();
            System.out.println("Found " + allMenus.size() + " active menus");
            
            for (Menu menu : allMenus) {
                if (menu.getSections() != null) {
                    List<MenuSection> sortedSections = menu.getSections().stream()
                        .sorted((s1, s2) -> {
                            Integer order1 = s1.getOrderIndex() != null ? s1.getOrderIndex() : 0;
                            Integer order2 = s2.getOrderIndex() != null ? s2.getOrderIndex() : 0;
                            return order1.compareTo(order2);
                        })
                        .collect(Collectors.toList());
                    
                    menu.setSections(new HashSet<>(sortedSections));
                    
                    for (MenuSection section : menu.getSections()) {
                        if (section.getPlats() != null) {
                            section.getPlats().size();
                        }
                    }
                }
                
                if (menu.getDirectPlats() != null) {
                    menu.getDirectPlats().size();
                }
            }
            
            model.addAttribute("config", config);
            model.addAttribute("cartes", cartes);
            model.addAttribute("menus", allMenus);
            model.addAttribute("title", config.getRestaurantName() + " - Notre Carte");
            
            return "carte";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/config";
        }
    }

    @GetMapping("/plat/{id}")
    public String platDetails(@PathVariable Integer id, Model model) {
        try {
            Plat plat = platRepository.findById(id).orElse(null);
            if (plat == null) {
                return "redirect:/carte";
            }
            List<Review> reviews = reviewRepository.findByPlatOrderByDateDesc(plat);
            model.addAttribute("plat", plat);
            model.addAttribute("reviews", reviews);
            return "plat-details";
        } catch (Exception e) {
            return "redirect:/config";
        }
    }
} 