package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.JoinColumn; 
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class Review {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn (name= "plat_id")

  private String name;

  private String email;

  private Date date;
  
  private Number note;

  private String comment;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDate(Date date){
    this.date = date;
  }

  public Date getDate(){
    return date;
  }

  public void setNote(Number note){
    this.note = note;
  }

  public Number getNote(){
    return note;
  }

  public void  setComment(String comment){
    this.comment = comment;
  }

  public String getComment(){
    return comment;
  }
}