package com.example.foodservice;

import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.CuisineDoesNotExistsException;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
import com.example.foodservice.repository.CuisineRepo;
import com.example.foodservice.repository.DishRepo;
import com.example.foodservice.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FoodService
{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepo cuisineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DishRepo dishRepo;


    /**
     * This method is responsible for registering new Restaurants
     * @param restaurant
     * @return String
     */
    public Restaurant registerRestaurant(Restaurant restaurant)
    {
         Restaurant savedRestaurant;

        try {
              savedRestaurant = restaurantRepository.save(restaurant);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        if (savedRestaurant.getRest_id() > 0)
        {
            return savedRestaurant;
        }

        return null;
    }

    /**
     * Returns information about Restaurant
     * @return
     */
    public RestaurantDTO getRestaurant()
    {
        Optional<Restaurant> optional = restaurantRepository.findById(1);

        Restaurant restaurant = optional.get();

        RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);

        return restaurantDTO;
    }

    /**
     * Registering New Cuisine
     * @param cuisine
     * @return
     */
    public Cuisine registerCuisine(Cuisine cuisine)
    {
        Cuisine savedCuisine;

        try {

            savedCuisine = cuisineRepository.save(cuisine);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        if (savedCuisine.getId() > 0)
        {
            return savedCuisine;
        }

        return null;
    }


    public List<Dish> getCuisine(String name)
    {
        Optional<Cuisine> optional = cuisineRepository.findByName(name);

        if(!optional.isPresent())
        {
            throw new CuisineDoesNotExistsException("Cuisine not found: " + name);
        }

        Cuisine savedCuisine = optional.get();

        List<Dish> dishes = (List<Dish>) dishRepo.findAllByCuisine(savedCuisine.getName());

        return dishes;
    }

    public List<Cuisine> getAllCuisines()
    {
        List<Cuisine> cuisines = (List<Cuisine>) cuisineRepository.findAll();
        return cuisines;
    }

    /**
     * Registering New Dish
     * @param dish
     * @return
     */
    public Dish registerDish(Dish dish)
    {
        Dish savedDish;

            Optional<Cuisine> optional = cuisineRepository.findByName(dish.getCuisine());

            if(optional.isEmpty())
            {
                throw new CuisineDoesNotExistsException("Cuisine not found: " + dish.getCuisine());
            }

            dish.setCuisine_id(optional.get().getId());

            savedDish = dishRepo.save(dish);


        if (savedDish.getDish_id() > 0)
        {
            return savedDish;
        }

        return null;

    }

}
