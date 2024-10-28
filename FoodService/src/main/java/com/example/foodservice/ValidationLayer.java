package com.example.foodservice;

import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.CuisineAlreadyExistsException;
import com.example.foodservice.exceptions.DishAlreadyExistsException;
import com.example.foodservice.exceptions.InvalidRestaurantException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
import com.example.foodservice.repository.CuisineRepo;
import com.example.foodservice.repository.DishRepo;
import com.example.foodservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ValidationLayer
{
    @Autowired
    CuisineRepo cuisineRepo;
    @Autowired
    private DishRepo dishRepo;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public ValidationLayer() {
    }

    /**
     * This method is responsible for validating restaurant registration
     * @param restaurant
     * @return
     */
    public String validateRestaurant(RestaurantDTO restaurant) {

        String validationErrors="";

        if(restaurant.getName()==null)
        {
            throw new InvalidRestaurantException("Restaurant name is required");
        }

        if(restaurant.getAddress()==null)
        {
            throw new InvalidRestaurantException("Restaurant address is required");
        }

        if(restaurant.getPhone()==null)
        {
            throw new InvalidRestaurantException("Restaurant phone is required");
        }

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

    public String validateCuisine(Cuisine cuisine, Integer restId) {

        Optional<Restaurant> rest = restaurantRepository.findById(restId);

        List<Cuisine> cuisineList = rest.get().getCuisineList();

        for(Cuisine c: cuisineList)
        {
            if(c.getName().equals(cuisine.getName()))
            {
                throw new CuisineAlreadyExistsException(cuisine.getName() + "Cuisine already exists");

            }
        }

        rest.get().getCuisineList().add(cuisine);
        cuisine.setRestaurant(rest.get());

        cuisineRepo.save(cuisine);

        restaurantRepository.save(rest.get());

        return "" ;
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
