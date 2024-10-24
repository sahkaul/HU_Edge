package com.example.foodservice.controller;

import com.example.foodservice.FoodService;
import com.example.foodservice.MappingLayer;
import com.example.foodservice.ValidationLayer;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.exceptions.InvalidDishException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Cuisine APIS", description = "Create a cuisine and dishes belonging to Cusines")
public class CuisineController {

    @Autowired
    ValidationLayer validationLayer;

    @Autowired
    FoodService foodService;

    @Autowired
    MappingLayer mappingLayer;


    /**
     * Create new cuisine
     * @param cuisine
     * @return
     */
    @PostMapping("/registerCuisine")
    public ResponseEntity<?> registerCuisine(@RequestBody Cuisine cuisine) {

        String validationErrors = validationLayer.validateCuisine(cuisine);

        if (!validationErrors.isEmpty()) {
            throw new InvalidCuisineException("Input Invalid: " + validationErrors);
        }

        Cuisine registeredCuisine = foodService.registerCuisine(cuisine);

        return ResponseEntity.ok().body("Cuisine Created Successfully");

    }

    /**
     * Get all dishes belonging to a cuisine
     * @param cuisineName
     * @return
     */
    @GetMapping("/getCuisine")
    public ResponseEntity<?> getCuisine(@RequestParam("cuisine") String cuisineName) {

        List<Dish> dishes = foodService.getCuisine(cuisineName);

        return ResponseEntity.ok().body(dishes);

    }

    /**
     * Get Names of All Cuisines
     * @return
     */
    @GetMapping("/getAllCuisines")
    public ResponseEntity<?> getAllCuisines() {

        List<Cuisine> cuisineList = foodService.getAllCuisines();

        return ResponseEntity.ok().body(cuisineList);

    }


}