package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Header;
import com.example.repositories.HeaderRepository;

@RestController
@RequestMapping("/header")
public class HeaderController {


    @Autowired
    private HeaderRepository headerRepository;


    @GetMapping
    public List<Header> getUniqueHeader(){
        return headerRepository.findAll();
    }
    
}
