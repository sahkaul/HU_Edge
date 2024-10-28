package com.example.orderservice.repo;

import com.example.orderservice.model.OrderDetails;
import com.example.orderservice.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailsRepo extends CrudRepository<OrderDetails, Integer> {

    Iterable<OrderDetails> findAllByRestId(Integer restId);

}
