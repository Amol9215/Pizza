package com.pizzaChain.customer.repository;

import com.pizzaChain.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByEmail(String email);
    Optional<Customer> findByName(String username);
}
