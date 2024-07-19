package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.BasketMapper;
import com.example.shoppingmanager.repository.BasketRepository;
import com.example.shoppingmanager.repository.CustomerRepository;
import com.example.shoppingmanager.utils.LogMessages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketService {

    private final CustomerRepository customerRepository;
    private final BasketMapper basketMapper;
    private final BasketRepository basketRepository;

    @Transactional
    public Set<BasketResponseDto> addBasket(long customerId, String basketName) throws CustomerException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_ID, customerId);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_ID + customerId);
                });

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
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_ID, customerId);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_ID + customerId);
                });

        Basket basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> {
                    log.error(LogMessages.BASKET_NOT_FOUND_ID, basketId);
                    return new BasketException(LogMessages.BASKET_NOT_FOUND_ID + basketId);
                });
        customer.removeBasket(basket);
    }

    @Transactional
    public Set<BasketResponseDto> getBasketsByCustomerId(Long customerId) throws CustomerException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_ID, customerId);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_ID + customerId);
                });

        return customer.getBaskets()
                .stream()
                .map(basketMapper::basketToBasketResponseDto).collect(Collectors.toSet());
    }
}
