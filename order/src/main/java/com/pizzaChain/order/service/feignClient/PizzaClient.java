package com.pizzaChain.order.service.feignClient;

import com.pizzaChain.order.model.Pizza;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pizza-service", url="http://localhost:8082/pizza")
public interface PizzaClient {

    @GetMapping("/{pizzaId}")
    Pizza viewPizzaById(@PathVariable String pizzaId);
}
