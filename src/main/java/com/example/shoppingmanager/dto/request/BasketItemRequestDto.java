package com.example.shoppingmanager.dto.request;

import com.example.shoppingmanager.entity.Product;

public record BasketItemRequestDto(long basketId,
                                   ProductRequestDto product,
                                   int quantity) {
}