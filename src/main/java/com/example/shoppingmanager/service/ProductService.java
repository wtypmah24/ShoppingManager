package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.entity.Product;
import com.example.shoppingmanager.exception.ProductException;
import com.example.shoppingmanager.mapper.ProductMapper;
import com.example.shoppingmanager.repository.ProductRepository;
import com.example.shoppingmanager.utils.LogMessages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws ProductException {
        checkProductCandidates(productRequestDto);
        return mapper.productToProductResponseDto(
                repository.save(mapper.productRequestDtoToProduct(productRequestDto))
        );
    }

    @Transactional
    public void removeProductById(long id) {
        repository.deleteById(id);
    }

    public ProductResponseDto getProductById(long id) throws ProductException {
        return mapper.productToProductResponseDto(repository
                .findById(id)
                .orElseThrow(() -> {
                    log.error(LogMessages.PRODUCT_NOT_FOUND_ID, id);
                    return new ProductException(LogMessages.PRODUCT_NOT_FOUND_ID + id);
                }));
    }

    public ProductResponseDto getProductByProductName(String productName) throws ProductException {
        return mapper
                .productToProductResponseDto(
                        repository
                                .findByProductName(productName)
                                .orElseThrow(() -> {
                                    log.error(LogMessages.PRODUCT_NOT_FOUND_NAME, productName);
                                    return new ProductException
                                            (LogMessages.PRODUCT_NOT_FOUND_NAME + productName);
                                }));
    }

    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll().stream()
                .map(mapper::productToProductResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto updateProduct(long id, Map<String, Object> params) throws ProductException {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductException(LogMessages.PRODUCT_NOT_FOUND_ID + id));

        params.forEach((key, value) -> {
            switch (key) {
                case "productName" -> product.setProductName((String) value);
                case "price" -> {
                    if (value instanceof Double) {
                        product.setPrice(BigDecimal.valueOf((Double) value));
                    } else if (value instanceof BigDecimal) {
                        product.setPrice((BigDecimal) value);
                    } else {
                        log.error("Invalid type for price");
                        throw new IllegalArgumentException("Invalid type for price");
                    }
                }
            }
        });

        Product updatedProduct = repository.save(product);

        return mapper.productToProductResponseDto(updatedProduct);
    }

    private void checkProductCandidates(ProductRequestDto productRequestDto) throws ProductException {
        if (productRequestDto == null) {
            log.error("Wrong product request received");
            throw new ProductException("You didn't provide a product request!");
        }

        if (productRequestDto.productName().isEmpty() || productRequestDto.price() == null) {
            log.error("Wrong product request received");
            throw new ProductException("You didn't provide a product name or price!");
        }
    }
}
