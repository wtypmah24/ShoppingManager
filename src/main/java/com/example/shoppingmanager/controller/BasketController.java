package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.exception.BasketException;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/baskets")
public class BasketController {

    private final BasketService service;

    @PostMapping("/add/{customerId}")
    public Set<BasketResponseDto> addBasket(@PathVariable long customerId, @RequestParam String basketName)
            throws CustomerException {

        return service.addBasket(customerId, basketName);
    }

    @DeleteMapping("/remove/{customerId}/{basketId}")
    public void removeBasket(@PathVariable long customerId, @PathVariable long basketId)
            throws CustomerException, BasketException {

        service.removeBasket(customerId, basketId);
    }

    @GetMapping("/{customerId}")
    public Set<BasketResponseDto> getBasketsByCustomerId(@PathVariable long customerId) throws CustomerException {

        return service.getBasketsByCustomerId(customerId);
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
}