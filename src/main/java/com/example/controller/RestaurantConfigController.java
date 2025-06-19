package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entities.RestaurantConfig;
import com.example.repositories.RestaurantConfigRepository;
import com.example.storage.StorageFileNotFoundException;
import com.example.storage.StorageService;

@Controller
@RequestMapping("/admin")
public class RestaurantConfigController {

    private final StorageService storageService;
    
    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RestaurantConfigController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/config") 
    public String showConfigForm(Model model) {
        List<RestaurantConfig> existingConfigs = restaurantConfigRepository.findAll();
        RestaurantConfig config;
        
        if (!existingConfigs.isEmpty()) {
            config = existingConfigs.get(0);
        } else {
            config = new RestaurantConfig();
            config.setUrl("http://localhost:8080");
        }
        
        model.addAttribute("restaurantConfig", config);
        return "config"; 
    }

    @PostMapping("/config")
    public String handleConfigForm(@RequestParam("banner") MultipartFile bannerFile,
                                   RestaurantConfig restaurantConfig,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        
        try {
            if (bannerFile != null && !bannerFile.isEmpty()) {
                storageService.store(bannerFile);
                restaurantConfig.setPhoto(bannerFile.getOriginalFilename());
            }
            
            if (restaurantConfig.getPassword() != null && !restaurantConfig.getPassword().isEmpty()) {
                restaurantConfig.setPassword(passwordEncoder.encode(restaurantConfig.getPassword()));
            }
            
            restaurantConfigRepository.save(restaurantConfig);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Configuration enregistrée avec succès!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Erreur lors de l'enregistrement: " + e.getMessage());
        }
        
        return "redirect:/admin/config";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        
        if (file == null)
            return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
