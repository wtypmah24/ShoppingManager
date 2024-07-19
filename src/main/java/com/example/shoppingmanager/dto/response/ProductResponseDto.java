package com.example.shoppingmanager.dto.response;

import java.math.BigDecimal;

public record ProductResponseDto(long productCode,
                                 String productName,
                                 BigDecimal price) {
}
