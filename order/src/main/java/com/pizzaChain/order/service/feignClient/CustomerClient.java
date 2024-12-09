package com.pizzaChain.order.service.feignClient;

import com.pizzaChain.order.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8081/customer")
public interface CustomerClient {

    @GetMapping("/{customerId}")
    Customer viewCustomerById(@PathVariable String customerId);
}
