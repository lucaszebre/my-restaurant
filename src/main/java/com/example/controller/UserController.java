package com.example.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.entities.User;
import com.example.repositories.UserRepository;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id ){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("no user found"));
    }

    @GetMapping("/users")
    public List<User> getUniqUser(){
        return userRepository.findAll();
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, this endpoint is not secure";
    }

    @GetMapping("/user/userProfile")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    
}