package com.example.orderservice;

import com.example.orderservice.model.OrderDetails;
import com.example.orderservice.model.OrderHistoryDto;
import com.example.orderservice.model.Orders;
import com.example.orderservice.repo.OrderDetailsRepo;
import com.example.orderservice.repo.OrderRepo;
import com.example.orderservice.scheduler.FirstUpdateScheduler;
import com.example.orderservice.scheduler.SecondUpdateScheduler;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class OrderService {
    @Autowired
    EmailService emailService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderDetailsRepo orderDetailsRepo;

    @Autowired
    FirstUpdateScheduler firstUpdateScheduler;

    @Autowired
    SecondUpdateScheduler secondUpdateScheduler;

    public Orders placeOrder(Orders order, Integer userId, Integer restId, List<Integer> cuisines, List<Integer> dishes) {
        order.setUser_id(userId.longValue()); // Ensure the user ID is set correctly
        order.setOrder_status("Order Placed");
        order.setDate(LocalDate.now());

        // Save the order first to generate the order ID
        Orders savedOrder = orderRepo.save(order);

        for (int i = 0; i < dishes.size(); i++) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setRestId(restId);
            orderDetails.setCuisineId(cuisines.get(i));
            orderDetails.setDish_Id(dishes.get(i));
            orderDetails.setOrder(savedOrder); // Set the saved order with a valid ID
            orderDetailsRepo.save(orderDetails);
        }

        /* emailService.sendEmail("sahilkaul52@gmail.com", "Sahil-Humais Project", "Order Placed"); */

        firstUpdateScheduler.scheduleOrderUpdate(savedOrder.getId());
        secondUpdateScheduler.scheduleOrderUpdate(savedOrder.getId());
        // Call to payment ms, generate billing data
        // Create compound response of order and billing data

        return savedOrder;
    }


    public boolean cancelOrder(Integer id) {

        Orders order = orderRepo.findById(id).orElseThrow(()-> new BadRequestException("Order not found"));

        String status = order.getOrder_status();

        if(status.equals("Order Placed"))
        {
            return true;
        }

        // firstUpdateScheduler.cancelOrderUpdate(order.getId());
        // secondUpdateScheduler.cancelOrderUpdate(order.getId());
        return false;
    }

    public List<OrderHistoryDto> restOrderHistory(Integer restId, Integer cuisineId, LocalDate inputDate)
    {
        Iterable<OrderDetails> orderDetailsList = orderDetailsRepo.findAllByRestId(restId);

        Iterator<OrderDetails> iterator = orderDetailsList.iterator();

        if(cuisineId!=null) {
            while (iterator.hasNext()) {
                OrderDetails orderDetails = iterator.next();

                if (orderDetails.getCuisineId() != cuisineId) {
                    iterator.remove();
                }

            }
        }

        if(inputDate!=null) {
            while (iterator.hasNext()) {
                OrderDetails orderDetails = iterator.next();

                if (!orderDetails.getOrder().getDate().equals(inputDate)) {
                    iterator.remove();
                }

            }
        }

        List<OrderHistoryDto> ordersHistoryList = new ArrayList<>();


       for(OrderDetails orderDetails: orderDetailsList)
       {
           ordersHistoryList.add(new OrderHistoryDto(orderDetails.getDish_Id(),orderDetails.getCuisineId()));
       }

       return ordersHistoryList;

    }


    public List<List<OrderDetails>> userOrderHistory(LocalDate inputDate)
    {
        Iterable<Orders> ordersList = orderRepo.findByDate(inputDate);

        List<List<OrderDetails>> list = new ArrayList<>();

        for(Orders order: ordersList)
        {
         list.add(order.getOrder_details());
        }

        return list;
    }
}

