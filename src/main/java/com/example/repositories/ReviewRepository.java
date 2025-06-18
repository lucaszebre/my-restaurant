package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    
}
