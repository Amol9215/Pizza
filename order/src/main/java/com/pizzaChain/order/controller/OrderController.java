package com.pizzaChain.order.controller;

import com.pizzaChain.order.exception.InvalidOrderException;
import com.pizzaChain.order.model.Orders;
import com.pizzaChain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
//@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order){
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String orderId) throws InvalidOrderException {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Orders> updateOrderById(@PathVariable String orderId) throws InvalidOrderException {
        return new ResponseEntity<>(orderService.updateOrderById(orderId), HttpStatus.OK) ;
    }

    @GetMapping("user/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable String customerId){
        return new ResponseEntity<>(orderService.getOrdersByCustomerId(customerId), HttpStatus.OK);
    }

}
