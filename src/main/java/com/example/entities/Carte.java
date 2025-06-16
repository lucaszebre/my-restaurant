package com.example.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cartes")
public class Carte {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @OneToMany(mappedBy = "carte")
    private Set<Menu> menus;



}