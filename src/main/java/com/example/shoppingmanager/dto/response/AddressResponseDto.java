package com.example.shoppingmanager.dto.response;

public record AddressResponseDto(long id,
                                 long customerId,
                                 String street,
                                 String city,
                                 int houseNumber,
                                 int zipCode) {
}
