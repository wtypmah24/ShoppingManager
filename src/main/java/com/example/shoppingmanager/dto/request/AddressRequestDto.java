package com.example.shoppingmanager.dto.request;

public record AddressRequestDto(String street,
                                String city,
                                int houseNumber,
                                int zipCode) {
}
