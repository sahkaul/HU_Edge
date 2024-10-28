package com.example.deliveryservice;

import com.example.deliveryservice.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController
{
    @Autowired
    DeliveryRepo deliveryRepository;

    @Autowired
    FirstUpdateScheduler firstUpdateScheduler;

    @PostMapping("/startDelivery")
    public void startDelivery(@RequestParam("order_id") Integer orderId)
    {
        Delivery delivery = new Delivery();
        delivery.setOrder_id(orderId);
        delivery.setDelivery_status("Agent is arriving to Pickup order");
        firstUpdateScheduler.scheduleOrderUpdate(orderId);
        deliveryRepository.save(delivery);


    }

}
