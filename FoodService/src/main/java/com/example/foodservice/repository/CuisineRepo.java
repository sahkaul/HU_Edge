package com.example.foodservice.repository;

import com.example.foodservice.model.Cuisine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuisineRepo extends CrudRepository<Cuisine, Integer> {

    Optional<Cuisine> findByName(String name);

}
