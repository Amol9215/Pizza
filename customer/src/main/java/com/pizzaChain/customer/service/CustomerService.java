package com.pizzaChain.customer.service;

import com.pizzaChain.customer.exception.InvalidCustomerException;
import com.pizzaChain.customer.model.Customer;

import java.util.Optional;

public interface CustomerService {

    public Customer addCustomer(Customer customer);
    public Customer viewCustomerById(String customerId) throws InvalidCustomerException;
    public Customer updateCustomerById(String customerId, Customer updateCustomer) throws InvalidCustomerException;
    public Customer getCustomerByEmail(String email) throws InvalidCustomerException;
    public Optional<Customer> getCustomerByName(String name) throws InvalidCustomerException;
}
