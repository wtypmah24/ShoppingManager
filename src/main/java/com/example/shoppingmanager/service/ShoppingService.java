package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.BasketItem;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.entity.Product;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.exception.ProductException;
import com.example.shoppingmanager.mapper.BasketItemMapper;
import com.example.shoppingmanager.repository.BasketItemRepository;
import com.example.shoppingmanager.repository.BasketRepository;
import com.example.shoppingmanager.repository.CustomerRepository;
import com.example.shoppingmanager.repository.ProductRepository;
import com.example.shoppingmanager.utils.LogMessages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingService {

    private final BasketRepository basketRepository;
    private final BasketItemMapper basketItemMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;

    //Add items to the specific basket
    @Transactional
    public Set<BasketItemResponseDto> addItemsToBasket(long basketId,
                                                       Map<String, Integer> itemsToQuantity)
            throws BasketException, ProductException {

        Basket basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> {
                    log.error(LogMessages.BASKET_NOT_FOUND_ID, basketId);
                    return new BasketException(LogMessages.BASKET_NOT_FOUND_ID + basketId);
                });

        Set<BasketItem> items = createBasketItems(basket, itemsToQuantity);

        return basket.addItemsToBasket(items)
                .stream()
                .map(basketItemMapper::basketItemToBasketItemResponseDto)
                .collect(Collectors.toSet());
    }

    //Add items to a new basket
    @Transactional
    public Set<BasketItemResponseDto> addItemsToNewBasket(Long customerId,
                                                          String basketName,
                                                          Map<String, Integer> itemsToQuantity)
            throws CustomerException, ProductException {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_ID, customerId);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_ID + customerId);
                });

        Basket basket = new Basket();
        basket.setName(basketName);
        customer.addBasket(basket);
        basket.setCustomer(customer);
        basket = basketRepository.save(basket);
        Set<BasketItem> items = createBasketItems(basket, itemsToQuantity);
        System.out.println("Items: " + items);

        return basket.addItemsToBasket(items)
                .stream()
                .map(basketItemMapper::basketItemToBasketItemResponseDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public BasketItemResponseDto removeItemFromBasket(long basketId, long productId)
            throws BasketException, ProductException {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> {
                    log.error(LogMessages.BASKET_NOT_FOUND_ID, basketId);
                    return new BasketException(LogMessages.BASKET_NOT_FOUND_ID + basketId);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error(LogMessages.PRODUCT_NOT_FOUND_ID, productId);
                    return new ProductException(LogMessages.PRODUCT_NOT_FOUND_ID + productId);
                });

        BasketItem itemToRemove = basket.getProducts()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No basket item found for product with id: {} in basket with id: {}", productId, basketId);
                    return new IllegalArgumentException("Product not found in basket");
                });

        basket.getProducts().remove(itemToRemove);

        return basketItemMapper.basketItemToBasketItemResponseDto(itemToRemove);
    }

    private Set<BasketItem> createBasketItems(Basket basket, Map<String, Integer> itemsToQuantity)
            throws ProductException {

        Set<BasketItem> items = new HashSet<>();
        for (Map.Entry<String, Integer> entry : itemsToQuantity.entrySet()) {
            int productCode = Integer.parseInt(entry.getKey());
            Integer quantity = entry.getValue();

            Product product = productRepository
                    .findById(productCode)
                    .orElseThrow(() -> {
                        log.error(LogMessages.PRODUCT_NOT_FOUND_ID, productCode);
                        return new ProductException(LogMessages.PRODUCT_NOT_FOUND_ID + productCode);
                    });

            BasketItem basketItem = new BasketItem();
            basketItem.setProduct(product);
            basketItem.setBasket(basket);
            basketItem.setQuantity(quantity);

            BasketItem savedBasketItem = basketItemRepository.save(basketItem);

            items.add(savedBasketItem);
        }

        return items;
    }
}