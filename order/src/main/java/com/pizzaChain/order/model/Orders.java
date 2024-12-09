package com.pizzaChain.order.model;

import com.pizzaChain.order.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    private String id;
    private String customerId;
    private LocalDateTime orderDate;
    private long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PizzaOrderItem> pizzaItems;

    @ElementCollection
    @CollectionTable(name = "order_status", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Set<Status> status;

    private LocalDateTime created_at;


    @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }
}
