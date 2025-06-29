package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.entities.Menu;
import com.example.entities.MenuSection;
import com.example.entities.Carte;
import com.example.entities.Plat;
import com.example.repositories.MenuRepository;
import com.example.repositories.MenuSectionRepository;
import com.example.repositories.CarteRepository;
import com.example.repositories.PlatRepository;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Controller
@RequestMapping("/admin/menus")
@PreAuthorize("hasRole('ADMIN')")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuSectionRepository menuSectionRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private PlatRepository platRepository;

    @GetMapping("")
    public String listMenus(Model model) {
        List<Menu> menus = menuRepository.findAll();
        model.addAttribute("menus", menus);
        return "admin/menus/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Carte> cartes = carteRepository.findAll();
        List<Plat> availablePlats = platRepository.findAll();
        
        Set<MenuSection> defaultSections = new HashSet<>();
        defaultSections.add(new MenuSection("Entrées", "Entrées du menu", 1));
        defaultSections.add(new MenuSection("Plats principaux", "Plats principaux du menu", 2));
        defaultSections.add(new MenuSection("Desserts", "Desserts du menu", 3));
        
        Menu menu = new Menu();
        menu.setSections(defaultSections);
        
        model.addAttribute("menu", menu);
        model.addAttribute("cartes", cartes);
        model.addAttribute("availablePlats", availablePlats);
        return "admin/menus/form";
    }

    @PostMapping("/new")
    public String createMenu(@ModelAttribute("menu") Menu menu, 
                           BindingResult bindingResult, 
                           Model model) {
        if (bindingResult.hasErrors()) {
            List<Carte> cartes = carteRepository.findAll();
            List<Plat> availablePlats = platRepository.findAll();
            model.addAttribute("cartes", cartes);
            model.addAttribute("availablePlats", availablePlats);
            return "admin/menus/form";
        }
        
        menuRepository.save(menu);
        
        if (menu.getSections() != null) {
            for (MenuSection section : menu.getSections()) {
                section.setMenu(menu);
                menuSectionRepository.save(section);
            }
        }
        
        return "redirect:/admin/menus";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
        
        List<Carte> cartes = carteRepository.findAll();
        List<Plat> availablePlats = platRepository.findAll();
        
        model.addAttribute("menu", menu);
        model.addAttribute("cartes", cartes);
        model.addAttribute("availablePlats", availablePlats);
        return "admin/menus/form";
    }

    @PostMapping("/edit/{id}")
    public String updateMenu(@PathVariable Integer id, 
                           @ModelAttribute("menu") Menu menu, 
                           BindingResult bindingResult, 
                           Model model) {
        if (bindingResult.hasErrors()) {
            List<Carte> cartes = carteRepository.findAll();
            List<Plat> availablePlats = platRepository.findAll();
            model.addAttribute("cartes", cartes);
            model.addAttribute("availablePlats", availablePlats);
            return "admin/menus/form";
        }
        
        menu.setId(id);
        menuRepository.save(menu);
        
        if (menu.getSections() != null) {
            for (MenuSection section : menu.getSections()) {
                section.setMenu(menu);
                menuSectionRepository.save(section);
            }
        }
        
        return "redirect:/admin/menus";
    }

    @PostMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Integer id) {
        menuRepository.deleteById(id);
        return "redirect:/admin/menus";
    }

    @PostMapping("/toggle/{id}")
    public String toggleMenu(@PathVariable Integer id) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
        
        menu.setActive(!menu.isActive());
        menuRepository.save(menu);
        return "redirect:/admin/menus";
    }

    @GetMapping("/{menuId}/sections")
    public String manageSections(@PathVariable Integer menuId, Model model) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
        
        List<Plat> availablePlats = platRepository.findAll();
        
        model.addAttribute("menu", menu);
        model.addAttribute("availablePlats", availablePlats);
        return "admin/menus/sections";
    }
} 