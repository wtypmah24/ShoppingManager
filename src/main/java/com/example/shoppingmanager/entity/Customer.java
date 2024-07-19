package com.example.shoppingmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"baskets"})
@ToString(exclude = {"baskets"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"))
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Basket> baskets = new HashSet<>();

    public Set<Basket> addBasket(Basket basket) {
        baskets.add(basket);
        return baskets;
    }

    public void removeBasket(Basket basket) {
        baskets.remove(basket);
    }
}