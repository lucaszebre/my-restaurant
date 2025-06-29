package com.example.entities;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.OrderBy;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;
    
    private String description;
    
    private Double price;
    
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name= "carte_id")
    private Carte carte;

    @OneToMany(mappedBy="menu", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private Set<MenuSection> sections;

    @OneToMany(mappedBy="menu")
    private Set<Plat> directPlats;

    public Menu() {}
    
    public Menu(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Set<MenuSection> getSections() {
        return sections;
    }

    public void setSections(Set<MenuSection> sections) {
        this.sections = sections;
    }

    public Set<Plat> getDirectPlats() {
        return directPlats;
    }

    public void setDirectPlats(Set<Plat> directPlats) {
        this.directPlats = directPlats;
    }

    public List<Plat> getAllPlats() {
        List<Plat> allPlats = new ArrayList<>();
        
        if (directPlats != null) {
            allPlats.addAll(directPlats);
        }
        
        if (sections != null) {
            for (MenuSection section : sections) {
                if (section.getPlats() != null) {
                    allPlats.addAll(section.getPlats());
                }
            }
        }
        
        return allPlats;
    }
}