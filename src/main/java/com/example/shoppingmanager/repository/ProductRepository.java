package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteById(long id);

    Optional<Product> findById(long id);

    Optional<Product> findByProductName(String productName);
}