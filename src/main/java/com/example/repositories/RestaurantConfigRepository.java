package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.RestaurantConfig;

public interface RestaurantConfigRepository extends JpaRepository<RestaurantConfig, Integer> {
    
} 