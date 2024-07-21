package com.example.shoppingmanager.dto.response;

import com.example.shoppingmanager.dto.request.AddressRequestDto;

import java.util.Set;

public record CustomerResponseDto(long id,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  Set<AddressResponseDto> addresses) {
}
