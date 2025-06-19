package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity 
@Table(name = "restaurant_config")
public class RestaurantConfig {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String url;

    private String color;

    private String photo;

    private String password;

    @Transient
    private String banner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name){
        this.name= name;
    }

    public String getName(){
        return name;
    }

    public void setUrl(String url){
        this.url = url ;
    }

    public String getUrl(){
        return url;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    public void setColor(String color){
        this.color=color;
    }
    
    public String getColor(){
        return color;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }

    public String getPhoto(){
        return photo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
