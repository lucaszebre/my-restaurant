package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Carte;

public interface CarteRepository extends JpaRepository<Carte, Integer>{
    
}
