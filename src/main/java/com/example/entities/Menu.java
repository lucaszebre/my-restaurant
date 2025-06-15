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
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn (name= "carte_id")

    @OneToMany(mappedBy="menu")
    private Set<Plat> plats;



}