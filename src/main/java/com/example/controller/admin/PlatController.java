package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.entities.Plat;
import com.example.entities.Menu;
import com.example.entities.Carte;
import com.example.repositories.PlatRepository;
import com.example.repositories.MenuRepository;
import com.example.repositories.CarteRepository;

import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/admin/plats")
@PreAuthorize("hasRole('ADMIN')")
public class PlatController {

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CarteRepository carteRepository;

    private static final String UPLOAD_DIR = "uploads/plats/";

    @GetMapping("")
    public String listPlats(Model model) {
        try {
            List<Plat> plats = platRepository.findAll();
            model.addAttribute("plats", plats);
        } catch (Exception e) {
            model.addAttribute("plats", new ArrayList<>());
        }
        return "admin/plats/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        try {
            List<Menu> menus = menuRepository.findAll();
            List<Carte> cartes = carteRepository.findAll();
            model.addAttribute("menus", menus);
            model.addAttribute("cartes", cartes);
        } catch (Exception e) {
            model.addAttribute("menus", new ArrayList<>());
            model.addAttribute("cartes", new ArrayList<>());
        }
        model.addAttribute("plat", new Plat());
        return "admin/plats/form";
    }

    @PostMapping("/new")
    public String createPlat(@Valid @ModelAttribute("plat") Plat plat, 
                           BindingResult bindingResult, 
                           @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                           @RequestParam(value = "allergens", required = false) String allergens,
                           Model model) {
        if (bindingResult.hasErrors()) {
            try {
                List<Menu> menus = menuRepository.findAll();
                List<Carte> cartes = carteRepository.findAll();
                model.addAttribute("menus", menus);
                model.addAttribute("cartes", cartes);
            } catch (Exception e) {
                model.addAttribute("menus", new ArrayList<>());
                model.addAttribute("cartes", new ArrayList<>());
            }
            return "admin/plats/form";
        }
        
        if (allergens != null && !allergens.trim().isEmpty()) {
            plat.setAllergens(allergens);
        }
        
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String fileName = saveFile(photoFile);
                plat.setPhoto(fileName);
            } catch (IOException e) {
                bindingResult.rejectValue("photo", "error.photo", "Erreur lors de l'upload de la photo");
                try {
                    List<Menu> menus = menuRepository.findAll();
                    List<Carte> cartes = carteRepository.findAll();
                    model.addAttribute("menus", menus);
                    model.addAttribute("cartes", cartes);
                } catch (Exception ex) {
                    model.addAttribute("menus", new ArrayList<>());
                    model.addAttribute("cartes", new ArrayList<>());
                }
                return "admin/plats/form";
            }
        }
        
        try {
            platRepository.save(plat);
        } catch (Exception e) {
            bindingResult.reject("error.database", "Erreur lors de la sauvegarde: " + e.getMessage());
            try {
                List<Menu> menus = menuRepository.findAll();
                List<Carte> cartes = carteRepository.findAll();
                model.addAttribute("menus", menus);
                model.addAttribute("cartes", cartes);
            } catch (Exception ex) {
                model.addAttribute("menus", new ArrayList<>());
                model.addAttribute("cartes", new ArrayList<>());
            }
            return "admin/plats/form";
        }
        
        return "redirect:/admin/plats";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        try {
            Plat plat = platRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plat not found"));
            
            List<Menu> menus = menuRepository.findAll();
            List<Carte> cartes = carteRepository.findAll();
            model.addAttribute("plat", plat);
            model.addAttribute("menus", menus);
            model.addAttribute("cartes", cartes);
        } catch (Exception e) {
            return "redirect:/admin/plats";
        }
        return "admin/plats/form";
    }

    @PostMapping("/edit/{id}")
    public String updatePlat(@PathVariable Integer id, 
                           @Valid @ModelAttribute("plat") Plat plat, 
                           BindingResult bindingResult, 
                           @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                           @RequestParam(value = "allergens", required = false) String allergens,
                           Model model) {
        if (bindingResult.hasErrors()) {
            try {
                List<Menu> menus = menuRepository.findAll();
                List<Carte> cartes = carteRepository.findAll();
                model.addAttribute("menus", menus);
                model.addAttribute("cartes", cartes);
            } catch (Exception e) {
                model.addAttribute("menus", new ArrayList<>());
                model.addAttribute("cartes", new ArrayList<>());
            }
            return "admin/plats/form";
        }
        
        if (allergens != null && !allergens.trim().isEmpty()) {
            plat.setAllergens(allergens);
        }
        
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String fileName = saveFile(photoFile);
                plat.setPhoto(fileName);
            } catch (IOException e) {
                bindingResult.rejectValue("photo", "error.photo", "Erreur lors de l'upload de la photo");
                try {
                    List<Menu> menus = menuRepository.findAll();
                    List<Carte> cartes = carteRepository.findAll();
                    model.addAttribute("menus", menus);
                    model.addAttribute("cartes", cartes);
                } catch (Exception ex) {
                    model.addAttribute("menus", new ArrayList<>());
                    model.addAttribute("cartes", new ArrayList<>());
                }
                return "admin/plats/form";
            }
        }
        
        try {
            plat.setId(id);
            platRepository.save(plat);
        } catch (Exception e) {
            bindingResult.reject("error.database", "Erreur lors de la sauvegarde: " + e.getMessage());
            try {
                List<Menu> menus = menuRepository.findAll();
                List<Carte> cartes = carteRepository.findAll();
                model.addAttribute("menus", menus);
                model.addAttribute("cartes", cartes);
            } catch (Exception ex) {
                model.addAttribute("menus", new ArrayList<>());
                model.addAttribute("cartes", new ArrayList<>());
            }
            return "admin/plats/form";
        }
        
        return "redirect:/admin/plats";
    }

    @PostMapping("/delete/{id}")
    public String deletePlat(@PathVariable Integer id) {
        try {
            platRepository.deleteById(id);
        } catch (Exception e) {
        }
        return "redirect:/admin/plats";
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