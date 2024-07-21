package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketItemMapper basketItemMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BasketItemRepository basketItemRepository;

    @InjectMocks
    private ShoppingService shoppingService;

    private Basket basket;
    private Customer customer;
    private Product product;
    private BasketItem basketItem;
    private BasketItemResponseDto basketItemResponseDto;

    private ProductResponseDto productResponse;


    @BeforeEach
    void setUp() {
        // Setup Basket
        basket = new Basket();
        basket.setId(1L);
        basket.setName("Test Basket");

        // Setup Customer
        customer = new Customer();
        customer.setId(1L);

        // Setup Product
        product = new Product();
        product.setProductCode(1L);
        product.setProductName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));

        // Setup BasketItem
        basketItem = new BasketItem();
        basketItem.setProduct(product);
        basketItem.setBasket(basket);
        basketItem.setQuantity(1);

        //Setup product response dto
        productResponse = new ProductResponseDto(1L, "Test Product", BigDecimal.valueOf(100.00));

        // Setup BasketItemResponseDto
        basketItemResponseDto = new BasketItemResponseDto(1L, productResponse, 1);
    }

    @Test
    void addItemsToBasket() throws BasketException, ProductException {
        Map<String, Integer> itemsToQuantity = Map.of("1", 2);

        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(basketItemRepository.save(any(BasketItem.class))).thenReturn(basketItem);
        when(basketItemMapper.basketItemToBasketItemResponseDto(basketItem)).thenReturn(basketItemResponseDto);

        Set<BasketItemResponseDto> response = shoppingService.addItemsToBasket(1L, itemsToQuantity);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.contains(basketItemResponseDto));

        verify(basketRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(basketItemRepository, times(1)).save(any(BasketItem.class));
        verify(basketItemMapper, times(1)).basketItemToBasketItemResponseDto(basketItem);
    }

    @Test
    void addItemsToNewBasket() throws CustomerException, ProductException {
        Map<String, Integer> itemsToQuantity = Map.of("1", 2);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);
        when(basketItemRepository.save(any(BasketItem.class))).thenReturn(basketItem);
        when(basketItemMapper.basketItemToBasketItemResponseDto(basketItem)).thenReturn(basketItemResponseDto);

        Set<BasketItemResponseDto> response = shoppingService.addItemsToNewBasket(1L, "New Basket", itemsToQuantity);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.contains(basketItemResponseDto));

        verify(customerRepository, times(1)).findById(1L);
        verify(basketRepository, times(1)).save(any(Basket.class));
        verify(productRepository, times(1)).findById(1L);
        verify(basketItemRepository, times(1)).save(any(BasketItem.class));
        verify(basketItemMapper, times(1)).basketItemToBasketItemResponseDto(basketItem);
    }

    @Test
    void removeItemFromBasket() throws BasketException, ProductException {
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(basketItemMapper.basketItemToBasketItemResponseDto(basketItem)).thenReturn(basketItemResponseDto);

        basket.getProducts().add(basketItem);

        BasketItemResponseDto response = shoppingService.removeItemFromBasket(1L, 1L);

        assertNotNull(response);
        assertEquals(basketItemResponseDto, response);

        verify(basketRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(basketItemMapper, times(1)).basketItemToBasketItemResponseDto(basketItem);
    }
}