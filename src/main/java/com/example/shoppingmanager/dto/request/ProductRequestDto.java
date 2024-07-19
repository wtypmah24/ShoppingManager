package com.example.shoppingmanager.dto.request;

import java.math.BigDecimal;

public record ProductRequestDto(long productCode,
                                String productName,
                                BigDecimal price) {
}