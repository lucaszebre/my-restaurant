package com.example.entities;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity 
@Table(name = "plats")
public class Plat {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn (name= "menu_id")
  private Menu menu;

  private String name;

  private String photo;

  
  private Number price;

  @ElementCollection
  private String[] listAllergenes;


  @OneToMany(mappedBy = "plat")
  private Set<Review> reviews;


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

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }


  public void setPrice(Number price){
    this.price = price;
  }

  public Number getPrice(){
    return price;
  }

}