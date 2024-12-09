package com.pizzaChain.pizza.service.impl;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import com.pizzaChain.pizza.exceptions.InvalidPizzaException;
import com.pizzaChain.pizza.model.Pizza;
import com.pizzaChain.pizza.repository.PizzaRepository;
import com.pizzaChain.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Pizza createPizza(Pizza pizza) {
        String pizzaId = UUID.randomUUID().toString();
        pizza.setId(pizzaId);
        return pizzaRepository.save(pizza);
    }

    @Override
    public Pizza updatePizzaById(String pizzaId, Pizza updatedPizza) throws InvalidPizzaException {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(()->new InvalidPizzaException("Pizza not found with id:"+pizzaId));
        pizza.setName(updatedPizza.getName());
        pizza.setPrice(updatedPizza.getPrice());
        pizza.setDescription(updatedPizza.getDescription());
        pizza.setUpdatedAt(LocalDateTime.now());
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Pizza> viewAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza viewPizzaById(String pizzaId) throws InvalidPizzaException {
        return pizzaRepository.findById(pizzaId)
                .orElseThrow(()->new InvalidPizzaException("Pizza not found with Id:"+pizzaId));
    }

    @Override
    public String deletePizza(String pizzaId) {
        pizzaRepository.deleteById(pizzaId);
        return "Pizza deleted successfully";
    }

    @Override
    public List<Pizza> getPizzasByType(PizzaType type) {
        return pizzaRepository.findByTypeContaining(type);
    }

    @Override
    public List<Pizza> getPizzasBySize(PizzaSize size) {
        return pizzaRepository.findBySizeContaining(size);
    }
}
