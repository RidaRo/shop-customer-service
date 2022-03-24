package com.example.training.shop.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ItemDTO {
    private String code;
    private String name;
    private BigDecimal price;
    private Long quantity;
}
