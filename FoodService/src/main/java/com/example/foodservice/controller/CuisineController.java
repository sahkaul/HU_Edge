package com.example.foodservice.controller;

import com.example.foodservice.FoodService;
import com.example.foodservice.MappingLayer;
import com.example.foodservice.ValidationLayer;
import com.example.foodservice.dto.MenuResponseDTO;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.exceptions.InvalidDishException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
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
    public ResponseEntity<?> registerCuisine(@RequestBody Cuisine cuisine,@RequestParam("restId") Integer restId) {

        String validationErrors = validationLayer.validateCuisine(cuisine,restId);

        if (!validationErrors.isEmpty()) {
            throw new InvalidCuisineException("Input Invalid: " + validationErrors);
        }

        Cuisine registeredCuisine = foodService.registerCuisine(cuisine,restId);

        return ResponseEntity.ok().body(registeredCuisine);

    }

    /**
     * Get all dishes belonging to a cuisine
     * @param cuisineName
     * @return
     */
    @GetMapping("/getCuisine")
    public ResponseEntity<?> getCuisine(@RequestParam("cuisine") String cuisineName) {

        Cuisine cuisine =  foodService.getCuisine(cuisineName);

        return ResponseEntity.ok().body(cuisine);

    }

    /**
     * Get Names of All Cuisines
     * @return
     */
    @GetMapping("/getAllCuisines")
    public ResponseEntity<?> getAllCuisines(@RequestParam("restId") Integer restId) {

        Restaurant restaurant = foodService.getAllCuisines(restId);

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();

        menuResponseDTO.setCuisineList(restaurant.getCuisineList());

        return ResponseEntity.ok().body(menuResponseDTO);

    }


}