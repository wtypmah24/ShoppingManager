package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.entity.Product;
import com.example.shoppingmanager.exception.ProductException;
import com.example.shoppingmanager.mapper.ProductMapper;
import com.example.shoppingmanager.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    @ExtendWith(MockitoExtension.class)
    private ProductService productService;

    private Product product;
    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductCode(1L);
        product.setProductName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));

        productRequestDto = new ProductRequestDto(
                1L,
                "Test Product",
                BigDecimal.valueOf(100.00));
        productResponseDto = new ProductResponseDto(
                1L,
                "Test Product",
                BigDecimal.valueOf(100.00));
    }

    @Test
    void addProduct() throws ProductException {
        when(mapper.productRequestDtoToProduct(productRequestDto)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);
        when(mapper.productToProductResponseDto(product)).thenReturn(productResponseDto);

        ProductResponseDto response = productService.addProduct(productRequestDto);

        assertNotNull(response);
        assertEquals(productResponseDto, response);

        verify(mapper, times(1)).productRequestDtoToProduct(productRequestDto);
        verify(repository, times(1)).save(product);
        verify(mapper, times(1)).productToProductResponseDto(product);
    }

    @Test
    void removeProductById() {
        doNothing().when(repository).deleteById(1L);

        productService.removeProductById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void getProductById() throws ProductException {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.productToProductResponseDto(product)).thenReturn(productResponseDto);

        ProductResponseDto response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(productResponseDto, response);

        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).productToProductResponseDto(product);
    }

    @Test
    void getProductByProductName() throws ProductException {
        when(repository.findByProductName("Test Product")).thenReturn(Optional.of(product));
        when(mapper.productToProductResponseDto(product)).thenReturn(productResponseDto);

        ProductResponseDto response = productService.getProductByProductName("Test Product");

        assertNotNull(response);
        assertEquals(productResponseDto, response);

        verify(repository, times(1)).findByProductName("Test Product");
        verify(mapper, times(1)).productToProductResponseDto(product);
    }

    @Test
    void getAllProducts() {
        List<Product> products = List.of(product);
        when(repository.findAll()).thenReturn(products);
        when(mapper.productToProductResponseDto(product)).thenReturn(productResponseDto);

        List<ProductResponseDto> response = productService.getAllProducts();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.stream().anyMatch(dto -> dto.equals(productResponseDto)));

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).productToProductResponseDto(product);
    }

    @Test
    void updateProduct() throws ProductException {
        Map<String, Object> params = new HashMap<>();
        params.put("productName", "Updated Product");
        params.put("price", 150.00);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);
        when(mapper.productToProductResponseDto(product)).thenReturn(productResponseDto);

        ProductResponseDto response = productService.updateProduct(1L, params);

        assertNotNull(response);
        assertEquals(productResponseDto, response);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(product);
        verify(mapper, times(1)).productToProductResponseDto(product);
    }
}