package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BasketRepositoryTest {

    @Autowired
    BasketRepository basketRepository;

    Basket basket;

    @BeforeEach
    void setUp() {
        basket = new Basket();
        basket.setName("Test Basket");
    }

    @Test
    void saveBasket() {
        Basket savedBasket = basketRepository.save(basket);
        assert basketRepository.findById(savedBasket.getId()).isPresent();
    }

    @Test
    void removeBasketById() {
        basketRepository.save(basket);
        basketRepository.deleteById(basket.getId());
        assert basketRepository.findById(basket.getId()).isEmpty();
    }
}
