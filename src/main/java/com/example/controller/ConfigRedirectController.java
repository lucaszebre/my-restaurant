package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigRedirectController {

    @GetMapping("/config") 
    public String redirectToAdminConfig() {
        return "redirect:/admin/config";
    }
} 