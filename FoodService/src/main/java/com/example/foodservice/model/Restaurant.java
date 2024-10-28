package com.example.foodservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "restaurant")
    private List<Cuisine> cuisineList;

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishList;

    @Override
    public String toString() {
        return "Restaurant{" +
                "rest_id=" + rest_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
