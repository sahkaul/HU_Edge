package com.example.orderservice;


import com.example.orderservice.model.OrderDetails;
import com.example.orderservice.model.OrderHistoryDto;
import com.example.orderservice.model.Orders;
import com.example.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepo orderRepo;


    @PostMapping("/{userId}/placeOrder")
    public ResponseEntity<?> placeOrder(@PathVariable("userId") Integer userId,
            @RequestBody Orders order, @RequestParam("restId") Integer restId,
            @RequestParam("cuisines") List<Integer> cuisines, @RequestParam("dishes") List<Integer> dishes)
    {

        //jwt token
        Orders placeOrder = orderService.placeOrder(order, userId, restId, cuisines, dishes);

        return ResponseEntity.ok(placeOrder);

    }

    @GetMapping("/cancelOrder")
    public ResponseEntity<?> cancelOrder(@RequestParam("id") Integer id) {

        Boolean flag = orderService.cancelOrder(id);

        if (flag) {
            return ResponseEntity.ok("Order cancelled");
        }

        return ResponseEntity.ok("Too late. Order not cancelled");
    }

    @GetMapping("/{userId}/orderhistory")
    public ResponseEntity<?> userOrderHistory(@PathVariable("userId") Integer userId,
                                              @RequestParam("date") LocalDate date) {

        List<List<OrderDetails>> list = orderService.userOrderHistory(date);

        return ResponseEntity.ok(list);

    }

    @GetMapping("/{userId}/restorderhistory")
    public ResponseEntity<?> restaurantOrderHistory(@RequestParam("restId") Integer restId,
    @RequestParam(value = "cuisineId", required = false) Integer cuisineId, @RequestParam(value = "date", required = false) LocalDate date) {

        List<OrderHistoryDto> ordersHistoryList = orderService.restOrderHistory(restId,cuisineId,date);

        return ResponseEntity.ok(ordersHistoryList);

    }


    @PostMapping("/delivered")
    public ResponseEntity<?> orderDelivered(@RequestParam("id") Integer id) {

        Optional<Orders> optional = orderRepo.findById(id);

        if (optional.isPresent()) {
            Orders order = optional.get();
            order.setOrder_status("Order delivered");
            orderRepo.save(order);
        }

        return ResponseEntity.ok("Order delivered");

    }




}