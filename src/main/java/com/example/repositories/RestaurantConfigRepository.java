package com.example.repositories;

import com.example.entities.RestaurantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantConfigRepository extends JpaRepository<RestaurantConfig, Long> {
    RestaurantConfig findFirstByOrderByIdAsc();
    
    boolean existsByConfiguredTrue();
} 