package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import com.example.entities.RestaurantConfig;
import com.example.repositories.RestaurantConfigRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class RestaurantConfigController {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    private static final String UPLOAD_DIR = "uploads/config/";

    @GetMapping("/login")
    public String login(Model model) {
        var restaurantConfig = restaurantConfigRepository.findFirstByOrderByIdAsc();
        if (restaurantConfig != null && restaurantConfig.isConfigured()) {
            model.addAttribute("adminUsername", restaurantConfig.getRestaurantName());
            model.addAttribute("isConfigured", true);
        } else {
            model.addAttribute("adminUsername", "admin");
            model.addAttribute("isConfigured", false);
        }
        return "login";
    }

    @GetMapping("/config") 
    public String showConfigForm(Model model) {
        RestaurantConfig existingConfig = restaurantConfigRepository.findFirstByOrderByIdAsc();
        
        if (existingConfig != null && existingConfig.isConfigured()) {
            model.addAttribute("restaurantConfig", existingConfig);
            model.addAttribute("isConfigured", true);
        } else {
            RestaurantConfig restaurantConfig = existingConfig != null ? existingConfig : new RestaurantConfig();
            if (restaurantConfig.getSiteUrl() == null || restaurantConfig.getSiteUrl().trim().isEmpty()) {
            }
            if (restaurantConfig.getColor() == null || restaurantConfig.getColor().trim().isEmpty()) {
                restaurantConfig.setColor("#667eea");
            }
            model.addAttribute("restaurantConfig", restaurantConfig);
            model.addAttribute("isConfigured", false);
        }
        
        return "config"; 
    }

    @PostMapping("/config")
    public String saveConfigInitial(@ModelAttribute("restaurantConfig") RestaurantConfig restaurantConfig, 
                                   BindingResult bindingResult, 
                                   @ModelAttribute("bannerPhoto") MultipartFile bannerPhoto,
                                   Model model) {
        RestaurantConfig existingConfig = restaurantConfigRepository.findFirstByOrderByIdAsc();
        if (existingConfig != null && existingConfig.isConfigured()) {
            return "redirect:/admin/config";
        }
        
        return saveConfig(restaurantConfig, bindingResult, bannerPhoto, model, true);
    }

    @PostMapping("/admin/config")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveConfigAdmin(@ModelAttribute("restaurantConfig") RestaurantConfig restaurantConfig, 
                                 BindingResult bindingResult, 
                                 @ModelAttribute("bannerPhoto") MultipartFile bannerPhoto,
                                 Model model) {
        return saveConfig(restaurantConfig, bindingResult, bannerPhoto, model, false);
    }

    private String saveConfig(RestaurantConfig restaurantConfig, BindingResult bindingResult, 
                            MultipartFile bannerPhoto, Model model, boolean isInitialConfig) {
        if (restaurantConfig.getRestaurantName() == null || restaurantConfig.getRestaurantName().trim().isEmpty()) {
            bindingResult.rejectValue("restaurantName", "error.restaurantName", "Le nom du restaurant est requis");
        }
        
        if (restaurantConfig.getAdminPassword() != null && restaurantConfig.getAdminPassword().length() > 0 
            && restaurantConfig.getAdminPassword().length() < 8) {
            bindingResult.rejectValue("adminPassword", "error.adminPassword", "Le mot de passe doit contenir au moins 8 caractères");
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("isConfigured", restaurantConfig.isConfigured());
            return "config";
        }
        
        if (bannerPhoto != null && !bannerPhoto.isEmpty()) {
            try {
                String fileName = saveFile(bannerPhoto);
                restaurantConfig.setBannerPhotoPath(fileName);
            } catch (IOException e) {
                bindingResult.rejectValue("bannerPhoto", "error.bannerPhoto", "Erreur lors de l'upload de la photo");
                model.addAttribute("isConfigured", restaurantConfig.isConfigured());
                return "config";
            }
        }
        
        restaurantConfig.setConfigured(true);
        
        restaurantConfigRepository.save(restaurantConfig);
        
        if (isInitialConfig) {
            return "redirect:/";
        } else {
            model.addAttribute("successMessage", "Configuration sauvegardée avec succès!");
            model.addAttribute("isConfigured", true);
            return "config";
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }
}
