package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Plat;
import com.example.entities.Carte;

import java.util.List;

public interface PlatRepository extends JpaRepository<Plat, Integer> {
    
    List<Plat> findByCarte(Carte carte);
    
}
