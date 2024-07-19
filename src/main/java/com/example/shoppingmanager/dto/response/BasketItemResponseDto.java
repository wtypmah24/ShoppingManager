package com.example.shoppingmanager.dto.response;

public record BasketItemResponseDto(long id, ProductResponseDto product, int quantity) {
}