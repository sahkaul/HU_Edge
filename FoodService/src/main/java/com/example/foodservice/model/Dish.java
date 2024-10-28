package com.example.foodservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dish")
@Data
public class Dish
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dish_id;

    private String name;

    private float price;

    @ManyToOne
    @JoinColumn(name = "rest_fk")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "cuisine_fk")
    @JsonIgnore
    private Cuisine cuisine;

    @Override
    public String toString() {
        return "Dish{name='" + name + "'}";
    }

}
