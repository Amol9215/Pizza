package com.pizzaChain.customer.controller;

import com.pizzaChain.customer.exception.InvalidCustomerException;
import com.pizzaChain.customer.model.Customer;
import com.pizzaChain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> viewCustomerById(@PathVariable String customerId) throws InvalidCustomerException {
        return new ResponseEntity<>(customerService.viewCustomerById(customerId),HttpStatus.OK);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId,@RequestBody Customer updateCustomer) throws InvalidCustomerException {
        return new ResponseEntity<>(customerService.updateCustomerById(customerId,updateCustomer), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) throws InvalidCustomerException {
        return new ResponseEntity<>(customerService.getCustomerByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Optional<Customer> getCustomerByName(@PathVariable String name) throws InvalidCustomerException {
        return customerService.getCustomerByName(name);
    }
}
