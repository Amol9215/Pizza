package com.pizzaChain.pizza.service;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import com.pizzaChain.pizza.exceptions.InvalidPizzaException;
import com.pizzaChain.pizza.model.Pizza;

import java.util.List;

public interface PizzaService {

    public Pizza createPizza(Pizza pizza);
    public Pizza updatePizzaById(String pizzaId, Pizza updatedPizza) throws InvalidPizzaException;
    public List<Pizza> viewAllPizzas();
    public Pizza viewPizzaById(String pizzaId) throws InvalidPizzaException;
    public String deletePizza(String pizzaId);
    public List<Pizza> getPizzasByType(PizzaType type);
    public List<Pizza> getPizzasBySize(PizzaSize size);
}
