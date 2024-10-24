package com.example.foodservice.controller;

import com.example.foodservice.FoodService;
import com.example.foodservice.MappingLayer;
import com.example.foodservice.ValidationLayer;
import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.exceptions.InvalidDishException;
import com.example.foodservice.exceptions.InvalidRestaurantException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Dish APIS", description = "Create and add a dish to a cuisine")

public class DishController {

    @Autowired
    ValidationLayer validationLayer;

    @Autowired
    FoodService foodService;

    @Autowired
    MappingLayer mappingLayer;


    @PostMapping("/registerDish")
    public ResponseEntity<?> registerDish(@RequestBody Dish dish) {

        String validationErrors = validationLayer.validateDish(dish);

        if (!validationErrors.isEmpty()) {
            throw new InvalidDishException("Dish Invalid: " + validationErrors);
        }

        Dish registeredDish = foodService.registerDish(dish);

        return ResponseEntity.ok().body("Dish Added Successfully");

    }


}