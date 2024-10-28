package com.example.deliveryservice;


import com.example.deliveryservice.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends CrudRepository<Delivery, Integer>{

}
