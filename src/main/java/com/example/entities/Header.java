package com.example.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "headers")
public class Header {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String name;

  private String banner;

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

  public String getBannerPhoto() {
    return banner;
  }

  public void setBanner(String Banner) {
    this.banner = banner;
  }




}