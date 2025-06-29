package com.example.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cartes")
public class Carte {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;
    
    private String description;
    
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "carte")
    private Set<Menu> menus;

    @OneToMany(mappedBy = "carte")
    private Set<Plat> plats;

    public Carte() {}
    
    public Carte(String name, String description) {
        this.name = name;
        this.description = description;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Plat> getPlats() {
        return plats;
    }

    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }
}