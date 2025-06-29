package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.MenuSection;
import com.example.entities.Menu;

import java.util.List;

public interface MenuSectionRepository extends JpaRepository<MenuSection, Integer> {
    
    List<MenuSection> findByMenuOrderByOrderIndexAsc(Menu menu);
} 