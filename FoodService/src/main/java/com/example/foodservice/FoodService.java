package com.example.foodservice;

import com.example.foodservice.dto.ResponseDTO;
import com.example.foodservice.dto.RestaurantDTO;
import com.example.foodservice.exceptions.CuisineDoesNotExistsException;
import com.example.foodservice.exceptions.InvalidCuisineException;
import com.example.foodservice.exceptions.InvalidRestaurantException;
import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import com.example.foodservice.model.Restaurant;
import com.example.foodservice.repository.CuisineRepo;
import com.example.foodservice.repository.DishRepo;
import com.example.foodservice.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

/*        Optional<Restaurant> optional = restaurantRepository.findByName(restaurant.getName());

        if(!optional.isEmpty())
        {
            throw new InvalidRestaurantException("This restaurant already exists");
        }*/

        savedRestaurant = restaurantRepository.save(restaurant);


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
    public RestaurantDTO getRestaurant(Integer rest_id)
    {
        Optional<Restaurant> optional = restaurantRepository.findById(rest_id);

        Restaurant restaurant = optional.get();

        RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);

        return restaurantDTO;
    }

    /**
     * Registering New Cuisine
     * @param cuisine
     * @return
     */
    public Cuisine registerCuisine(Cuisine cuisine,Integer rest_id)
    {
        Cuisine savedCuisine;

        Restaurant restaurant = restaurantRepository.findById(rest_id).orElseThrow(()-> new InvalidRestaurantException("Restaurant not found: " + rest_id)) ;

        cuisine.setRestaurant(restaurant);

        savedCuisine = cuisineRepository.save(cuisine);

        if (savedCuisine.getId() > 0)
        {
            return savedCuisine;
        }

        return null;
    }


    public Cuisine getCuisine(String name)
    {
        Optional<Cuisine> optional = cuisineRepository.findByName(name);

        if(!optional.isPresent())
        {
            throw new CuisineDoesNotExistsException("Cuisine not found: " + name);
        }

        Cuisine savedCuisine = optional.get();

        return savedCuisine;
    }

    public Restaurant getAllCuisines(Integer restId)
    {
        Optional<Restaurant> optional = restaurantRepository.findById(restId);

        if (optional.isEmpty())
        {
            throw new InvalidRestaurantException("Restuarant does not exist");
        }

        return optional.get();
    }

    /**
     * Registering New Dish
     * @param dish
     * @return
     */
    public Dish registerDish(Dish dish, Integer restId, Integer cuisineId)
    {
            Dish savedDish;

            Optional<Cuisine> optionalCuisine = cuisineRepository.findById(cuisineId);

            if(optionalCuisine.isEmpty())
            {
                throw new CuisineDoesNotExistsException("Cuisine not found: " + dish.getCuisine());
            }
            dish.setCuisine(optionalCuisine.get());

            Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow(()-> new InvalidRestaurantException("Restaurant not found: " + restId)) ;
            dish.setRestaurant(restaurant);

        savedDish = dishRepo.save(dish);

        if (savedDish.getDish_id() > 0)
        {
            return savedDish;
        }

        return null;

    }


    public Restaurant getCuisines(Integer restaurantId)
    {
        Optional<Restaurant> optional = restaurantRepository.findById(restaurantId);

        if(optional.isEmpty())
        {
            throw new InvalidRestaurantException("Restaurant not found: " + restaurantId);
        }

        return optional.get();
    }

    public Restaurant getMenu(Integer restaurantId, List<String> inputCuisines, Integer inputPrice)
    {
       Optional<Restaurant> optional = restaurantRepository.findById(restaurantId);

       if(optional.isEmpty())
       {
           throw new InvalidRestaurantException("Restaurant not found: " + restaurantId);
       }

        Restaurant restaurant = optional.get();
        List<Cuisine> cuisineList = restaurant.getCuisineList();

        // Filter by input cuisines
        cuisineList = filterByCuisines(cuisineList, inputCuisines);

        // Filter by input price
        cuisineList = filterByPrice(cuisineList, inputPrice);

            restaurant.setCuisineList(cuisineList);

        return restaurant;
    }

    public List<ResponseDTO> getAllRestaurants(List<String> cuisineName)
    {
        Iterable<Restaurant> restaurantList = restaurantRepository.findAll();

        List<ResponseDTO> selectedRestaurants = new ArrayList<>();

        if(!cuisineName.isEmpty())
        {
            Iterator<Restaurant> iterator = restaurantList.iterator();

            while(iterator.hasNext())
            {
                Restaurant restaurant = iterator.next();

                ResponseDTO responseDTO = new ResponseDTO();
                responseDTO.setRestId(restaurant.getRest_id());

                for(Cuisine cuisine : restaurant.getCuisineList())
                {
                    if(cuisineName.contains(cuisine.getName()))
                    {
                        responseDTO.getCuisine().add(cuisine);
                    }
                }
                selectedRestaurants.add(responseDTO);
            }

            return selectedRestaurants;
        }

        for(Restaurant restaurant : restaurantList)
        {
            ResponseDTO responseDTO= new ResponseDTO();
            responseDTO.setRestId(responseDTO.getRestId());
            responseDTO.setCuisine(restaurant.getCuisineList());
            selectedRestaurants.add(responseDTO);
        }

        return selectedRestaurants;

    }

    public List<Cuisine> filterByPrice(List<Cuisine> cuisineList, Integer inputPrice) {
        if (inputPrice == null) {
            return cuisineList;
        }

        return cuisineList.stream()
                .map(cuisine -> {
                    List<Dish> filteredDishes = cuisine.getDishList().stream()
                            .filter(dish -> dish.getPrice() <= inputPrice)
                            .collect(Collectors.toList());
                    cuisine.setDishList(filteredDishes);
                    return cuisine;
                })
                .filter(cuisine -> !cuisine.getDishList().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Cuisine> filterByCuisines(List<Cuisine> cuisineList, List<String> inputCuisines) {
        if (inputCuisines == null || inputCuisines.isEmpty()) {
            return cuisineList;
        }

        return cuisineList.stream()
                .filter(cuisine -> inputCuisines.contains(cuisine.getName()))
                .collect(Collectors.toList());
    }

}
