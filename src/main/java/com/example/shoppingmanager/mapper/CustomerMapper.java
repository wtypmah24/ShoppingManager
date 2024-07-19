package com.example.shoppingmanager.mapper;

import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.request.ProductRequestDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.dto.response.ProductResponseDto;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto customerToCustomerResponseDto(Customer customer);
}
