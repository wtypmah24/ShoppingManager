package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.exception.ProductException;
import com.example.shoppingmanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto)
            throws ProductException {

        return productService.addProduct(productRequestDto);
    }

    @DeleteMapping("/remove")
    public void removeProductById(@RequestParam long id) {
        productService.removeProductById(id);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable long id) throws ProductException {

        return productService.getProductById(id);
    }

    @GetMapping("/name/{productName}")
    public ProductResponseDto getProductByProductName(@PathVariable String productName)
            throws ProductException {

        return productService.getProductByProductName(productName);
    }

    @GetMapping("/all")
    public Iterable<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/update/{id}")
    public ProductResponseDto updateProduct(@PathVariable long id,
                                            @RequestBody Map<String, Object> params)
            throws ProductException {

        return productService.updateProduct(id, params);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(ProductException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}