package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.mapper.BasketMapper;
import com.example.shoppingmanager.repository.BasketRepository;
import com.example.shoppingmanager.repository.CustomerRepository;
import com.example.shoppingmanager.service.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BasketService basketService;
    @MockBean
    private BasketRepository basketRepository;
    @MockBean
    private BasketMapper basketMapper;
    @MockBean
    private CustomerRepository customerRepository;
    private Set<BasketResponseDto> baskets;
    @BeforeEach
    void setUp() {
        ProductResponseDto product = new ProductResponseDto(
                123L,
                "Sample Product",
                new BigDecimal("19.99"));

        BasketItemResponseDto basketItem = new BasketItemResponseDto(1L, product, 2);
        Set<BasketItemResponseDto> items = new HashSet<>();
        items.add(basketItem);

        baskets = new HashSet<>();
        baskets.add(new BasketResponseDto(1L, "Basket 1", items));
        baskets.add(new BasketResponseDto(2L, "Basket 2", new HashSet<>()));
    }

    @Test
    void addBasket() throws Exception {
        long customerId = 1L;
        String basketName = "New Basket";

        when(basketService.addBasket(anyLong(), anyString())).thenReturn(baskets);
        mockMvc.perform(post("/baskets/add/{customerId}", customerId)
                        .param("basketName", basketName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(baskets));
    }
}