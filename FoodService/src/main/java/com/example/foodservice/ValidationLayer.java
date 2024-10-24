package com.example.foodservice;

import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.CuisineAlreadyExistsException;
import com.example.foodservice.exceptions.DishAlreadyExistsException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.repository.CuisineRepo;
import com.example.foodservice.repository.DishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationLayer
{
    @Autowired
    CuisineRepo cuisineRepo;
    @Autowired
    private DishRepo dishRepo;

    public ValidationLayer() {
    }

    /**
     * This method is responsible for validating restaurant registration
     * @param restaurant
     * @return
     */
    public String validateRestaurant(RestaurantDTO restaurant) {

        String validationErrors="";

        if(restaurant.getPhone().length() != 10)
        {
          validationErrors = validationErrors + "Phone number must be 10 digits\n";
        }

        if (restaurant.getName().length() < 5)
        {
            validationErrors=validationErrors + "Name must be at least 5 characters\n";
        }

        return validationErrors;
    }

    public String validateCuisine(Cuisine cuisine) {

        String validationErrors="";

        Optional<Cuisine> optional = cuisineRepo.findByName(cuisine.getName());

        if(optional.isPresent())
        {
            throw new CuisineAlreadyExistsException(cuisine.getName() + "Cuisine already exists");
        }


        if (cuisine.getName().length() < 5)
        {
            validationErrors=validationErrors + "Name must be at least 5 characters\n";
        }

        return validationErrors;
    }

    /**
     * Validating dish registration
     * @param dish
     * @return
     */
    public String validateDish(Dish dish) {

        String validationErrors="";

        Optional<Dish> optional = dishRepo.findByName(dish.getName());

        if(optional.isPresent())
        {
            throw new DishAlreadyExistsException(dish.getName() + "Dish already exists");
        }

        if(dish.getName().length() < 3)
        {
            validationErrors = validationErrors + "Dish Name must be 3 digits\n";
        }

        return validationErrors;
    }
}
