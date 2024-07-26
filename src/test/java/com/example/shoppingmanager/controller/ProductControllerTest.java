package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        productRequestDto = new ProductRequestDto(
                1L,
                "Sample Product",
                new BigDecimal("19.99"));
        productResponseDto = new ProductResponseDto(
                1L,
                "Sample Product",
                new BigDecimal("19.99"));
    }

    @Test
    void addProduct() throws Exception {
        when(productService.addProduct(any(ProductRequestDto.class))).thenReturn(productResponseDto);

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(productResponseDto));
    }

    @Test
    void removeProductById() throws Exception {
        doNothing().when(productService).removeProductById(anyLong());

        mockMvc.perform(delete("/products/remove")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        long productId = 1L;
        when(productService.getProductById(anyLong())).thenReturn(productResponseDto);

        mockMvc.perform(get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(productResponseDto));
    }

    @Test
    void getProductByProductName() throws Exception {
        String productName = "Sample Product";
        when(productService.getProductByProductName(anyString())).thenReturn(productResponseDto);

        mockMvc.perform(get("/products/name/{productName}", productName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(productResponseDto));
    }

    @Test
    void getAllProducts() throws Exception {

        List<ProductResponseDto> products = List.of(productResponseDto);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(products));
    }

    @Test
    void updateProduct() throws Exception {
        long productId = 1L;
        Map<String, Object> params = Map.of("name", "Updated Product", "price", new BigDecimal("29.99"));
        when(productService.updateProduct(anyLong(), any(Map.class))).thenReturn(productResponseDto);

        mockMvc.perform(put("/products/update/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(productResponseDto));
    }
}
