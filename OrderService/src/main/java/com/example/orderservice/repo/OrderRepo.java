package com.example.orderservice.repo;


import com.example.orderservice.model.OrderDetails;
import com.example.orderservice.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderRepo extends CrudRepository<Orders, Integer>
{

    Iterable<Orders> findByDate(LocalDate inputDate);
}
