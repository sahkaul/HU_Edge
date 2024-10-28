package com.example.orderservice.scheduler;

import com.example.orderservice.feign.DeliveryFeignClient;

import com.example.orderservice.model.Orders;
import com.example.orderservice.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Component
public class SecondUpdateScheduler
{
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    DeliveryFeignClient deliveryFeignClient;

    private Queue<Integer> queue = new LinkedList<>();

    public void scheduleOrderUpdate(Integer orderId) {
        queue.add(orderId);
    }

    @Transactional
    @Scheduled(cron = "0 */3 * * * *")
    public void secondOrderUpdate()
    {
        while(!queue.isEmpty()) {
            Integer orderId = queue.remove();
            Optional<Orders> optional = orderRepo.findById(orderId);
            Orders order = optional.get();
            order.setOrder_status("Order Ready");
            orderRepo.save(order);
            deliveryFeignClient.startDelivery(order.getId());
        }

    }

    public void cancelOrderUpdate(Integer orderId) {
        queue.remove(orderId);
    }
}
