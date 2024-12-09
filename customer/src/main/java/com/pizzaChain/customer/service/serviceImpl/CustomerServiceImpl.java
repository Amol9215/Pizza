package com.pizzaChain.customer.service.serviceImpl;

import com.pizzaChain.customer.exception.InvalidCustomerException;
import com.pizzaChain.customer.model.Customer;
import com.pizzaChain.customer.repository.CustomerRepository;
import com.pizzaChain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        String customerId = UUID.randomUUID().toString();

        customer.setCustomerId(customerId);
        return customerRepository.save(customer);
    }

    @Override
    public Customer viewCustomerById(String customerId) throws InvalidCustomerException {

        return customerRepository.findById(customerId).orElseThrow(()->new InvalidCustomerException("No customer with id "+customerId));
    }

    @Override
    public Customer updateCustomerById(String customerId, Customer updateCustomer) throws InvalidCustomerException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new InvalidCustomerException("No customer with Id:"+customerId));

        customer.setUpdatedAt(LocalDateTime.now());
        customer.setAddress(updateCustomer.getAddress());
        customer.setName(updateCustomer.getName());
        customer.setEmail(updateCustomer.getEmail());
        customer.setPhoneNo(updateCustomer.getPhoneNo());

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) throws InvalidCustomerException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer==null){
            throw new InvalidCustomerException("No customer found with email : "+email);
        }else {
            return customer;
        }
    }

    @Override
    public Optional<Customer> getCustomerByName(String name) throws InvalidCustomerException {
        Optional<Customer> customer = customerRepository.findByName(name);
        if(customer.isEmpty()){
            throw new InvalidCustomerException("No customer found with name : "+name);
        }else {
            return customer;
        }
    }

}
