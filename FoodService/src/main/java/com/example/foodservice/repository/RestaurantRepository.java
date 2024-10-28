package com.example.foodservice.repository;

import com.example.foodservice.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>
{

    Optional<Restaurant> findByName(String name);
}
