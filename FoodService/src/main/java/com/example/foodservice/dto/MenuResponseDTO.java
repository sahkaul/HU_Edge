package com.example.foodservice.dto;

import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponseDTO
{
    private List<Cuisine> cuisineList;

}
