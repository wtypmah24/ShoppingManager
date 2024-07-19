package com.example.shoppingmanager.mapper;

import com.example.shoppingmanager.dto.request.BasketItemRequestDto;
import com.example.shoppingmanager.dto.response.BasketItemResponseDto;
import com.example.shoppingmanager.entity.BasketItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketItemMapper {
    BasketItemResponseDto basketItemToBasketItemResponseDto(BasketItem basketItem);
}
