package com.example.foodservice;

import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MappingLayer
{
    private ModelMapper modelMapper;

    public MappingLayer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurant dtoToRestaurant(RestaurantDTO restaurantDTO)
    {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO restaurantToDTO(Restaurant restaurant)
    {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }


}
