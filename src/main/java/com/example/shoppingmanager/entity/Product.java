package com.example.shoppingmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_code", nullable = false, unique = true)
    private long productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}