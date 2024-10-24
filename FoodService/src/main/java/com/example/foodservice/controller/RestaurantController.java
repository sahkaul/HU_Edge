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
@Tag(name = "Restaurant APIS", description = "Register a Restauarnt and get information of a Restaurant")

public class RestaurantController {

    @Autowired
    ValidationLayer validationLayer;

    @Autowired
    FoodService foodService;

    @Autowired
    MappingLayer mappingLayer;

    /**
     * Register Restaurant
     * @param restaurantDTO
     * @return
     */
    @PostMapping("/registerRestaurant")
    public ResponseEntity<?> register(@RequestBody RestaurantDTO restaurantDTO) {


        String validationErrors = validationLayer.validateRestaurant(restaurantDTO);

        if (!validationErrors.isEmpty()) {
            throw new InvalidRestaurantException("Input Invalid: " + validationErrors);
        }

        Restaurant restaurant = mappingLayer.dtoToRestaurant(restaurantDTO);

        Restaurant registerRestaurant = foodService.registerRestaurant(restaurant);

        return ResponseEntity.ok().body("Restaurant Registered Successfully");

    }

    /**
     * Get info about restaurant
     * @return
     */
    @GetMapping("/restaurantInfo")
    public ResponseEntity<?> register() {

        RestaurantDTO restaurantDTO = foodService.getRestaurant();
        return ResponseEntity.ok().body(restaurantDTO);
    }


}