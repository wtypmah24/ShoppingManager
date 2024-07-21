package com.example.shoppingmanager.dto.response;

import com.example.shoppingmanager.dto.request.BasketItemRequestDto;

import java.util.Set;

public record BasketResponseDto(long id,
                                String name,
                                Set<BasketItemResponseDto> products) {
}