package com.example.shoppingmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "basket")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"customer", "products"})
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "basket_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "basket",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<BasketItem> products = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Set<BasketItem> addItemsToBasket(Set<BasketItem> items) {
        products.addAll(items);
        return products;
    }
}