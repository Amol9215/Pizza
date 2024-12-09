package com.pizzaChain.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String customerId;
    private String name;
    private String email;
    private long phoneNo;
    private String address;
    //    @CreationTimestamp
    private LocalDateTime createdAt;
    //    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
