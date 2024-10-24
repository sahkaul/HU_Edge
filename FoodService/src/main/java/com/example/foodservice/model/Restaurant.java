package com.example.foodservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "restaurant")
@Data
public class Restaurant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rest_id;

    private String name;

    private String address;

    private String phone;

}
