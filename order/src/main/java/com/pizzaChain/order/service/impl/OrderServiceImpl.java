package com.pizzaChain.order.service.impl;

import com.pizzaChain.order.enums.Status;
import com.pizzaChain.order.exception.InvalidOrderException;
import com.pizzaChain.order.model.Customer;
import com.pizzaChain.order.model.Orders;
import com.pizzaChain.order.model.Pizza;
import com.pizzaChain.order.model.PizzaOrderItem;
import com.pizzaChain.order.repository.OrderRepository;
import com.pizzaChain.order.repository.PizzaOrderItemRepository;
import com.pizzaChain.order.service.OrderService;
import com.pizzaChain.order.service.feignClient.CustomerClient;
import com.pizzaChain.order.service.feignClient.PizzaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaOrderItemRepository pizzaOrderItemRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private PizzaClient pizzaClient;

    @Override
    public Orders createOrder(Orders order) {

        Orders orders = new Orders();
        orders.setId(UUID.randomUUID().toString());
        long totalPrice = calculateTotalPrice(order.getPizzaItems(),order);
        orders.setCustomerId(order.getCustomerId());
        orders.setOrderDate(LocalDateTime.now());
        orders.setTotalPrice(totalPrice);

        List<PizzaOrderItem> pizzaItems = order.getPizzaItems();
        for (PizzaOrderItem item : pizzaItems) {
            item.setOrder(orders); // Set the parent reference
        }
        orders.setPizzaItems(pizzaItems);
        orders.setCreated_at(LocalDateTime.now());

        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.PENDING);

        orders.setStatus(statuses);

        return orderRepository.save(orders);
    }

    private long calculateTotalPrice(List<PizzaOrderItem> pizzaItems, Orders orderId) {
        long totalPrice = 0;
        for (PizzaOrderItem item : pizzaItems) {
            long pizzaPrice = fetchPizzaPrice(item.getPizzaId());
            totalPrice += pizzaPrice * item.getQuantity();
        }
        return totalPrice;
    }

    private long fetchPizzaPrice(String pizzaId) {
        return pizzaClient.viewPizzaById(pizzaId).getPrice();
    }

    @Override
    public Orders getOrderById(String orderId) throws InvalidOrderException {
        return orderRepository.findById(orderId).orElseThrow(()->new InvalidOrderException("No order with id:"+orderId));
    }

    @Override
    public Orders updateOrderById(String orderId) throws InvalidOrderException {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()->new InvalidOrderException("No order with Id:"+orderId));
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.DELIVERED);

        orders.setStatus(statuses);
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getOrdersByCustomerId(String customerId) {

        return orderRepository.findByCustomerId(customerId);
    }
}


