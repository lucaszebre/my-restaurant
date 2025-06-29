package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Review;
import com.example.entities.Plat;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    @Query("SELECT r FROM Review r ORDER BY r.date DESC")
    List<Review> findTop4ByOrderByDateDesc(Pageable pageable);
    
    @Query("SELECT r FROM Review r ORDER BY r.date DESC")
    List<Review> findAllByOrderByDateDesc();

    List<Review> findByPlatOrderByDateDesc(Plat plat);
}
