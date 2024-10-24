package com.example.foodservice.model;

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

    private int cuisine_id;

    private String cuisine;

}
