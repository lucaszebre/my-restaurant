package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; 
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.JoinColumn; 
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Carte {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn (name= "user_id")

    @OneToMany(mappedBy="carte")
    private Set<Menu> menus;



}