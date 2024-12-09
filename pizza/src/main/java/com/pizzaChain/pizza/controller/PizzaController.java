package com.pizzaChain.pizza.controller;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import com.pizzaChain.pizza.exceptions.InvalidPizzaException;
import com.pizzaChain.pizza.model.Pizza;
import com.pizzaChain.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/add")
    public ResponseEntity<Pizza> addPizza(@RequestBody Pizza pizza){
        return new ResponseEntity<>(pizzaService.createPizza(pizza), HttpStatus.OK);
    }

    @PutMapping("/{pizzaId}")
    public ResponseEntity<Pizza> updatePizza(@RequestBody Pizza updatedPizza, @PathVariable String pizzaId) throws InvalidPizzaException {
        return new ResponseEntity<>(pizzaService.updatePizzaById(pizzaId, updatedPizza), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Pizza>> viewAllPizzas(){
        return new ResponseEntity<>(pizzaService.viewAllPizzas(),HttpStatus.OK);
    }

    @GetMapping("/{pizzaId}")
    public ResponseEntity<Pizza> viewPizzaById(@PathVariable String pizzaId) throws InvalidPizzaException {
        return new ResponseEntity<>(pizzaService.viewPizzaById(pizzaId),HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Pizza>> getPizzasByType(@PathVariable PizzaType type){
        return new ResponseEntity<>(pizzaService.getPizzasByType(type), HttpStatus.OK);
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<Pizza>> getPizzasBySize(@PathVariable PizzaSize size){
        return new ResponseEntity<>(pizzaService.getPizzasBySize(size), HttpStatus.OK);
    }

    @DeleteMapping("/{pizzaId}")
    public ResponseEntity<String> deletePizzaById(@PathVariable String pizzaId){
        return new ResponseEntity<>(pizzaService.deletePizza(pizzaId), HttpStatus.OK);
    }
}
