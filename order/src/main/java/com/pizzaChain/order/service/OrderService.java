package com.pizzaChain.order.service;

import com.pizzaChain.order.exception.InvalidOrderException;
import com.pizzaChain.order.model.Orders;

import java.util.List;

public interface OrderService {

    public Orders createOrder(Orders order);
    public Orders getOrderById(String orderId) throws InvalidOrderException;
    public Orders updateOrderById(String orderId) throws InvalidOrderException;
    public List<Orders> getAllOrders();
    public List<Orders> getOrdersByCustomerId(String customerId);
}
