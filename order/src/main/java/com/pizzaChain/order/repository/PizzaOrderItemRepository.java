package com.pizzaChain.order.repository;

import com.pizzaChain.order.model.PizzaOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderItemRepository extends JpaRepository<PizzaOrderItem, Long> {
}
