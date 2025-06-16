package com.example.entities;

import jakarta.persistence.*;

import java.util.Set;
@Entity
@Table(name = "tables")
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn (name= "carte_id")

    @OneToMany(mappedBy="menu")
    private Set<Plat> plats;



}