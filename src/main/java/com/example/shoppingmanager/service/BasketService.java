package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.BasketMapper;
import com.example.shoppingmanager.repository.BasketRepository;
import com.example.shoppingmanager.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final CustomerRepository customerRepository;
    private final BasketMapper basketMapper;
    private final BasketRepository basketRepository;

    @Transactional
    public Set<BasketResponseDto> addBasket(long customerId, String basketName) throws CustomerException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerException("There is no customer with id: " + customerId));

        Basket basket = new Basket();
        basket.setName(basketName);
        basket.setCustomer(customer);

        return customer.addBasket(basket)
                .stream()
                .map(basketMapper::basketToBasketResponseDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void removeBasket(long customerId, long basketId) throws CustomerException, BasketException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerException("There is no customer with id: " + customerId));

        Basket basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new BasketException("There is no basket with id: " + basketId));
        customer.removeBasket(basket);
    }

    @Transactional
    public Set<BasketResponseDto> getBasketsByCustomerId(Long customerId) throws CustomerException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerException("There is no customer with id: " + customerId));

        return customer.getBaskets()
                .stream()
                .map(basketMapper::basketToBasketResponseDto).collect(Collectors.toSet());
    }
}
