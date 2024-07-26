package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.service.ShoppingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingController.class)
public class ShoppingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ShoppingService shoppingService;

    private Set<BasketItemResponseDto> basketItems;

    @BeforeEach
    void setUp() {
        ProductResponseDto product1 = new ProductResponseDto(
                1L,
                "Product 1",
                new BigDecimal("19.99"));
        ProductResponseDto product2 = new ProductResponseDto(
                2L,
                "Product 2",
                new BigDecimal("19.99"));

        basketItems = Set.of(
                new BasketItemResponseDto(1L, product1, 2),
                new BasketItemResponseDto(2L, product2, 1)
        );
    }

    @Test
    void addItemsToBasket() throws Exception {
        long basketId = 1L;
        Map<String, Integer> itemsToQuantity = Map.of("Product 1", 2, "Product 2", 1);

        when(shoppingService.addItemsToBasket(anyLong(), any(Map.class))).thenReturn(basketItems);

        mockMvc.perform(post("/shopping/add_to_existing_basket/{basketId}", basketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemsToQuantity)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(basketItems));
    }

    @Test
    void addItemsToNewBasket() throws Exception {
        long customerId = 1L;
        String basketName = "New Basket";
        Map<String, Integer> itemsToQuantity = Map.of("Product 1", 2, "Product 2", 1);

        when(shoppingService.addItemsToNewBasket(anyLong(), any(String.class), any(Map.class))).thenReturn(basketItems);

        mockMvc.perform(post("/shopping/add_to_basket/{customerId}", customerId)
                        .param("basketName", basketName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemsToQuantity)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(basketItems));
    }

    @Test
    void removeItemFromBasket() throws Exception {
        long basketId = 1L;
        long productId = 1L;
        ProductResponseDto product = new ProductResponseDto(1L, "Product 1", new BigDecimal("19.99"));
        BasketItemResponseDto removedItem = new BasketItemResponseDto(1L, product, 2);

        when(shoppingService.removeItemFromBasket(anyLong(), anyLong())).thenReturn(removedItem);

        mockMvc.perform(delete("/shopping/remove_from_basket/{basketId}/{productId}", basketId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(removedItem));
    }
}
