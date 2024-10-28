package com.example.foodservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cuisine
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String name;

   @ManyToOne
   @JoinColumn(name = "rest_fk")
   @JsonIgnore
   private Restaurant restaurant;

   @OneToMany(mappedBy = "cuisine")
   private List<Dish> dishList;

   @Override
   public String toString() {
      return "Cuisine{name='" + name + "'}";
   }
}
