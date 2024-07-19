package com.example.shoppingmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "basket_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"basket"})
@ToString(exclude = {"basket"})
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}