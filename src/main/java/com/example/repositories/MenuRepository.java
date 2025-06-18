package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{
    
}
