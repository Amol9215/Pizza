package com.pizzaChain.pizza.model;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    private String id;
    private String name;
    private String description;
    private long price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "pizza_type", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Set<PizzaType> type;

    @ElementCollection
    @CollectionTable(name = "pizza_size", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<PizzaSize> size;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }
}



