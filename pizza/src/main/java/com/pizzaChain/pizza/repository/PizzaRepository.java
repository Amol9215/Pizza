package com.pizzaChain.pizza.repository;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import com.pizzaChain.pizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, String> {

    List<Pizza> findByTypeContaining(PizzaType type);
    List<Pizza> findBySizeContaining(PizzaSize size);
}
