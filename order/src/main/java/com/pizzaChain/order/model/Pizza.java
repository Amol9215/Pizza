package com.pizzaChain.order.model;

import com.pizzaChain.order.model.enums.PizzaSize;
import com.pizzaChain.order.model.enums.PizzaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    private String id;
    private String name;
    private String description;
    private long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<PizzaType> type;

    private Set<PizzaSize> size;


}
