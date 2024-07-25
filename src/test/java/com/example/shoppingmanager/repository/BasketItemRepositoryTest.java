package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.BasketItem;
import com.example.shoppingmanager.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BasketItemRepositoryTest {

    @Autowired
    BasketItemRepository basketItemRepository;
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    ProductRepository productRepository;
    BasketItem basketItem;
    Product product;
    Basket basket;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductCode(1L);
        product.setProductName("Test product");
        product.setPrice(BigDecimal.valueOf(44.6));
        productRepository.save(product);

        basket = new Basket();
        basket.setName("Test basket");
        basketRepository.save(basket);


        basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setProduct(product);
        basketItem.setQuantity(1);
    }

    @Test
    void saveBasketItem() {
        BasketItem savedItem = basketItemRepository.save(basketItem);
        assertEquals(basketItem, savedItem);
    }
}
