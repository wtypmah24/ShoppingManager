package com.example.shoppingmanager.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private int houseNumber;
    private int zipCode;
}