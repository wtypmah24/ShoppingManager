package com.example.shoppingmanager.mapper;

import com.example.shoppingmanager.dto.request.BasketItemRequestDto;
import com.example.shoppingmanager.dto.response.BasketResponseDto;
import com.example.shoppingmanager.entity.Basket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    BasketResponseDto basketToBasketResponseDto(Basket basket);
}