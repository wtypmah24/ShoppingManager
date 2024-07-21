package com.example.shoppingmanager.dto.response;

public record AddressResponseDto(String street,
                                 String city,
                                 int houseNumber,
                                 int zipCode) {
}
