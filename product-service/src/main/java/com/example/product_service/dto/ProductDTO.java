package com.example.product_service.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    private String name;

    private double price;

    private int quantity;
    private boolean ageRestricted;
    
}
