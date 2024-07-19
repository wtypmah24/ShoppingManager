package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.exception.ProductException;
import com.example.shoppingmanager.service.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping")
public class ShoppingController {

    private final ShoppingService service;

    @PostMapping("/add_to_existing_basket/{basketId}")
    public Set<BasketItemResponseDto> addItemsToBasket(@PathVariable long basketId,
                                                       @RequestBody Map<String, Integer> itemsToQuantity)
            throws BasketException, ProductException {

        return service.addItemsToBasket(basketId, itemsToQuantity);
    }

    @PostMapping("/add_to_basket/{customerId}")
    public Set<BasketItemResponseDto> addItemsToNewBasket(@PathVariable long customerId,
                                                          @RequestParam String basketName,
                                                          @RequestBody Map<String, Integer> itemsToQuantity)
            throws CustomerException, ProductException {

        return service.addItemsToNewBasket(customerId, basketName, itemsToQuantity);
    }

    @DeleteMapping("/remove_from_basket/{basketId}/{productId}")
    public BasketItemResponseDto removeItemFromBasket(@PathVariable long basketId,
                                                      @PathVariable long productId)
            throws BasketException, ProductException {

        return service.removeItemFromBasket(basketId, productId);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> customerHandleException(CustomerException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> BasketHandleException(BasketException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> ProductHandleException(ProductException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}