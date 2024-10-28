package com.example.deliveryservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "OrderService")
public interface OrderFeignClient {

    @PostMapping("/delivered")
    ResponseEntity<String> orderDelivered(@RequestParam("id") Integer id);
}
