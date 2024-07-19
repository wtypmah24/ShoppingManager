package com.example.shoppingmanager.dto.request;

import java.util.Set;

public record CustomerRequestDto(String firstName,
                                 String lastName,
                                 String email,
                                 Set<AddressRequestDto> addresses) {
}