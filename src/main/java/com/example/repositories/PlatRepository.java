package com.example.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Plat;

public interface PlatRepository   extends  JpaRepository<Plat, Integer>{
    
}
