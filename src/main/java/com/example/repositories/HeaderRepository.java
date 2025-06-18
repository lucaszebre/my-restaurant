package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Header;

public interface HeaderRepository extends JpaRepository<Header, Long> {
    
}
