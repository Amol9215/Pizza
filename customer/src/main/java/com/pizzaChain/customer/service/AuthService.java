package com.pizzaChain.customer.service;

import com.pizzaChain.customer.model.Customer;
import com.pizzaChain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(Customer credential) {
        String custId = UUID.randomUUID().toString();
        credential.setCustomerId(custId);
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        customerRepository.save(credential);
        return "user registered successfully";
    }

    public String generateToken(String username) {  // this  is use  for login 
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {  // for validate token

        jwtService.validateToken(token);
    }

    public String logout(String token) {
        // Invalidate the token
        jwtService.invalidateToken(token);
        return "Logout successful";
    }
}


