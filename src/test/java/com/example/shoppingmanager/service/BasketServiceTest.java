package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.BasketMapper;
import com.example.shoppingmanager.repository.BasketRepository;
import com.example.shoppingmanager.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BasketMapper basketMapper;

    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketService basketService;

    private Customer customer;
    private Basket basket;
    private BasketResponseDto basketResponseDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setBaskets(new HashSet<>());

        basket = new Basket();
        basket.setId(1L);
        basket.setName("Test Basket");

        basketResponseDto = new BasketResponseDto(1L, "Test Basket", Collections.emptySet());
    }

    @Test
    void addBasket() throws CustomerException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(basketMapper.basketToBasketResponseDto(any(Basket.class))).thenReturn(basketResponseDto);

        Set<BasketResponseDto> response = basketService.addBasket(1L, "Test Basket");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.stream().anyMatch(b -> b.name().equals("Test Basket")));

        verify(customerRepository, times(1)).findById(1L);
        verify(basketMapper, times(1)).basketToBasketResponseDto(any(Basket.class));
    }

    @Test
    void removeBasket() throws CustomerException, BasketException {
        customer.addBasket(basket);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        assertTrue(customer.getBaskets().contains(basket));

        basketService.removeBasket(1L, 1L);

        verify(customerRepository, times(1)).findById(1L);
        verify(basketRepository, times(1)).findById(1L);

        assertFalse(customer.getBaskets().contains(basket));
    }

    @Test
    void getBasketsByCustomerId() throws CustomerException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(basketMapper.basketToBasketResponseDto(any(Basket.class))).thenReturn(basketResponseDto);

        basketService.addBasket(1L, "Test Basket");

        Set<BasketResponseDto> response = basketService.getBasketsByCustomerId(1L);

        assertNotNull(response);
        assertEquals(1, response.size());

        verify(customerRepository, times(2)).findById(1L);
        verify(basketMapper, times(2)).basketToBasketResponseDto(any(Basket.class));
    }
}