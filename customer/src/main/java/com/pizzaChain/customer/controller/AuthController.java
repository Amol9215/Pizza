package com.pizzaChain.customer.controller;

import com.pizzaChain.customer.dto.AuthRequest;
import com.pizzaChain.customer.exception.InvalidCustomerException;
import com.pizzaChain.customer.model.Customer;
import com.pizzaChain.customer.service.AuthService;
import com.pizzaChain.customer.service.CustomerService;
import com.pizzaChain.customer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody Customer user) {
        return service.saveUser(user);  // call authService of customer
    }


    @PostMapping("/token") // for login and  and verify the  username and password
    public Map<String, String> getToken(@RequestBody AuthRequest authRequest) {
        Map<String,String> map = new HashMap<>();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            map.put(service.generateToken(authRequest.getUsername()),authRequest.getUsername());
            return map;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Valid Token";
    }



    @PostMapping("/logout")
    public String logout(@RequestParam("token") String token) {
        jwtService.invalidateToken(token);
        return "Logout successful";
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> viewCustomerById(@PathVariable String customerId) throws InvalidCustomerException {
        return new ResponseEntity<>(customerService.viewCustomerById(customerId), HttpStatus.OK);
    }
}
