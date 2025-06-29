package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.entities.Carte;
import com.example.entities.Plat;
import com.example.repositories.CarteRepository;
import com.example.repositories.PlatRepository;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin/cartes")
@PreAuthorize("hasRole('ADMIN')")
public class CarteController {

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private PlatRepository platRepository;

    @GetMapping("")
    public String listCartes(Model model) {
        try {
            List<Carte> cartes = carteRepository.findAll();
            model.addAttribute("cartes", cartes);
        } catch (Exception e) {
            model.addAttribute("cartes", new ArrayList<>());
        }
        return "admin/cartes/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("carte", new Carte());
        return "admin/cartes/form";
    }

    @PostMapping("/new")
    public String createCarte(@ModelAttribute("carte") Carte carte, 
                            BindingResult bindingResult, 
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/cartes/form";
        }
        
        try {
            carteRepository.save(carte);
        } catch (Exception e) {
            bindingResult.reject("error.database", "Erreur lors de la sauvegarde: " + e.getMessage());
            return "admin/cartes/form";
        }
        
        return "redirect:/admin/cartes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        try {
            Carte carte = carteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte not found"));
            
            model.addAttribute("carte", carte);
        } catch (Exception e) {
            return "redirect:/admin/cartes";
        }
        return "admin/cartes/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCarte(@PathVariable Integer id, 
                            @ModelAttribute("carte") Carte carte, 
                            BindingResult bindingResult, 
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/cartes/form";
        }
        
        try {
            carte.setId(id);
            carteRepository.save(carte);
        } catch (Exception e) {
            bindingResult.reject("error.database", "Erreur lors de la sauvegarde: " + e.getMessage());
            return "admin/cartes/form";
        }
        
        return "redirect:/admin/cartes";
    }

    @GetMapping("/{id}/plats")
    public String viewPlatsByCarte(@PathVariable Integer id, Model model) {
        try {
            Carte carte = carteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte not found"));
            
            List<Plat> plats = platRepository.findByCarte(carte);
            model.addAttribute("carte", carte);
            model.addAttribute("plats", plats);
        } catch (Exception e) {
            return "redirect:/admin/cartes";
        }
        return "admin/cartes/plats";
    }

    @PostMapping("/delete/{id}")
    public String deleteCarte(@PathVariable Integer id) {
        try {
            carteRepository.deleteById(id);
        } catch (Exception e) {
        }
        return "redirect:/admin/cartes";
    }

    @PostMapping("/toggle/{id}")
    public String toggleCarte(@PathVariable Integer id) {
        try {
            Carte carte = carteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte not found"));
            
            carte.setActive(!carte.isActive());
            carteRepository.save(carte);
        } catch (Exception e) {
        }
        return "redirect:/admin/cartes";
    }
} 