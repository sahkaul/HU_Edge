package com.example.foodservice.repository;

import com.example.foodservice.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>
{

}
