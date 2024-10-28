package com.example.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "DeliveryService")
public interface DeliveryFeignClient {

    @PostMapping("/startDelivery")
    void startDelivery(@RequestParam("order_id") Integer orderId);
}
