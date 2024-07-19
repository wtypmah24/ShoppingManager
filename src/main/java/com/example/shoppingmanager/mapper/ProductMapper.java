package com.example.shoppingmanager.mapper;

import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productRequestDtoToProduct(ProductRequestDto productRequestDto);

    ProductResponseDto productToProductResponseDto(Product product);
}
