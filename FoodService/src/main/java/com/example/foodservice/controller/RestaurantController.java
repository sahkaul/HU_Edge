package com.example.foodservice.controller;

import com.example.foodservice.FoodService;
import com.example.foodservice.MappingLayer;
import com.example.foodservice.ValidationLayer;
import com.example.foodservice.dto.ErrorResponseDto;
import com.example.foodservice.dto.MenuResponseDTO;
import com.example.foodservice.dto.ResponseDTO;
import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.exceptions.InvalidDishException;
import com.example.foodservice.exceptions.InvalidRestaurantException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Register a new restaurant", description = "Registers a new restaurant and returns the registered restaurant details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered restaurant",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/registerRestaurant")
    public ResponseEntity<?> register(@RequestBody RestaurantDTO restaurantDTO) {

        String validationErrors = validationLayer.validateRestaurant(restaurantDTO);

        if (!validationErrors.isEmpty()) {
            throw new InvalidRestaurantException("Input Invalid: " + validationErrors);
        }

        Restaurant restaurant = mappingLayer.dtoToRestaurant(restaurantDTO);

        Restaurant registerRestaurant = foodService.registerRestaurant(restaurant);

        return ResponseEntity.ok().body(registerRestaurant);

    }

    /**
     * Get info about restaurant
     * @return
     */
    @GetMapping("{id}/restaurantInfo")
    public ResponseEntity<?> getInfo(@PathVariable("id") Integer rest_id) {

        RestaurantDTO restaurantDTO = foodService.getRestaurant(rest_id);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @GetMapping("/getAllRestaurants")
    public ResponseEntity<?> getAllRest(@RequestParam("cuisine") List<String> cuisine) {

        List<ResponseDTO> list = foodService.getAllRestaurants(cuisine);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getMenu(@RequestParam("Id") Integer restaurantId, @RequestParam(value = "cuisines", required = false) List<String> cuisines,
                                     @RequestParam(value = "price", required = false) Integer price)
    {
        Restaurant restaurant = foodService.getMenu(restaurantId, cuisines, price);

        restaurant.setDishList(null);
        return ResponseEntity.ok().body(restaurant);

    }



}