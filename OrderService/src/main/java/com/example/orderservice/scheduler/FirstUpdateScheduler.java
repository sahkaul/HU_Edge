package com.example.orderservice.scheduler;

import com.example.orderservice.model.Orders;
import com.example.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Component
public class FirstUpdateScheduler {

    @Autowired
    OrderRepo orderRepo;

    private Queue<Integer> queue = new LinkedList<>();

    public void scheduleOrderUpdate(Integer orderId) {
        queue.add(orderId);
    }

    @Scheduled(cron = "0 * * * * *")
    public void firstOrderUpdate()
    {
        while(!queue.isEmpty()) {
            Integer orderId = queue.remove();
            Optional<Orders> order = orderRepo.findById(orderId);
            order.get().setOrder_status("Order In Preparation");
            orderRepo.save(order.get());
        }

        }

    public void cancelOrderUpdate(Integer orderId) {
        queue.remove(orderId);
    }


}
