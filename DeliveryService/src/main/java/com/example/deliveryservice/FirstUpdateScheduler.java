package com.example.deliveryservice;

import com.example.deliveryservice.feign.OrderFeignClient;
import com.example.deliveryservice.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Component
public class FirstUpdateScheduler {

    @Autowired
    DeliveryRepo deliveryRepo;

    @Autowired
    OrderFeignClient orderFeignClient;

    private Queue<Integer> queue = new LinkedList<>();


    public void scheduleOrderUpdate(Integer orderId) {
        queue.add(orderId);
    }

    @Scheduled(cron = "0 * * * * *")
    public void firstOrderUpdate()
    {
        while(!queue.isEmpty()) {
            Optional<Delivery> optional = deliveryRepo.findById(queue.poll());
            Delivery delivery1 = optional.get();
            delivery1.setDelivery_status("Agent has delivered order");
            deliveryRepo.save(delivery1);
            orderFeignClient.orderDelivered(delivery1.getOrder_id());
        }

    }

    public void cancelOrderUpdate(Integer orderId) {
        queue.remove(orderId);
    }





}
