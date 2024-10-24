package com.example.foodservice.repository;

import com.example.foodservice.model.Cuisine;
import com.example.foodservice.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepo extends CrudRepository<Dish, Integer> {

    Optional<Dish> findByName(String dishName);

    Iterable<Dish> findAllByCuisine(String cuisine);
}
