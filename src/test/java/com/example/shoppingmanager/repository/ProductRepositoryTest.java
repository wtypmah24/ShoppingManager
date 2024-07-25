package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    Product product;
    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductCode(1L);
        product.setProductName("Test Product");
        product.setPrice(BigDecimal.valueOf(10.0));
    }

    @Test
    void deleteById() {
        productRepository.save(product);
        productRepository.deleteById(1L);
        assertFalse(productRepository.existsById(1L));
    }

    @Test
    void findById() {
        productRepository.save(product);
        assertEquals(product, productRepository.findById(1L).get());
    }

    @Test
    void findByProductName() {
        productRepository.save(product);
        assertEquals(product, productRepository.findByProductName("Test Product").get());
    }

    @Test
    void findAll(){
        productRepository.save(product);
        assertEquals(1, productRepository.findAll().size());
    }
}